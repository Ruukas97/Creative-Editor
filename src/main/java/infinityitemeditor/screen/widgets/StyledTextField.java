package infinityitemeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.styles.StyleVanilla;
import infinityitemeditor.util.GuiUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class StyledTextField extends Widget implements IRenderable, IGuiEventListener {
    private final FontRenderer fontRenderer;
    protected String text = "";
    @Getter
    @Setter
    protected String hint = null;
    protected int maxStringLength = 32;
    private int cursorCounter;
    private boolean canLoseFocus = true;
    private boolean isEnabled = true;
    private boolean shiftDown;
    private int lineScrollOffset;
    protected int cursorPosition;
    protected int selectionEnd;
    private String suggestion;
    private Consumer<String> guiResponder;
    protected Predicate<String> validator = s -> true;
    private BiFunction<String, Integer, String> textFormatter = (p_195610_0_, p_195610_1_) -> p_195610_0_;


    public StyledTextField(FontRenderer font, int x, int y, int width, int height, String msg) {
        this(font, x, y, width, height, null, msg);
    }


    public StyledTextField(FontRenderer fontIn, int x, int y, int width, int height, @Nullable StyledTextField old, String msg) {
        super(x, y, width, height, new StringTextComponent(msg));
        this.fontRenderer = fontIn;
        if (old != null) {
            this.setText(old.getText());
        }

    }


    public void setGuiResponder(Consumer<String> responder) {
        this.guiResponder = responder;
    }


    public void setTextFormatter(BiFunction<String, Integer, String> formatter) {
        this.textFormatter = formatter;
    }


    /**
     * Increments the cursor counter
     */
    public void tick() {
        ++this.cursorCounter;
    }


    /**
     * Sets the text of the textbox, and moves the cursor to the end.
     */
    public void setText(String textIn) {
        if (this.validator.test(textIn)) {
            if (textIn.length() > this.maxStringLength) {
                this.text = textIn.substring(0, this.maxStringLength);
            } else {
                this.text = textIn;
            }

            this.setCursorPositionEnd();
            this.setSelectionPos(this.cursorPosition);
            this.triggerResponder(textIn);
        }
    }


    /**
     * Returns the contents of the textbox
     */
    public String getText() {
        return this.text;
    }


    /**
     * returns the text between the cursor and selectionEnd
     */
    public String getSelectedText() {
        int i = Math.min(this.cursorPosition, this.selectionEnd);
        int j = Math.max(this.cursorPosition, this.selectionEnd);
        return this.text.substring(i, j);
    }


    public void setValidator(Predicate<String> p_200675_1_) {
        this.validator = p_200675_1_;
    }


    /**
     * Adds the given text after the cursor, or replaces the currently selected text
     * if there is a selection.
     */
    public void writeText(String textToWrite) {
        String s = "";
        String s1 = SharedConstants.filterText(textToWrite);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);
        if (!this.text.isEmpty()) {
            s = s + this.text.substring(0, i);
        }

        int l;
        if (k < s1.length()) {
            s = s + s1.substring(0, k);
            l = k;
        } else {
            s = s + s1;
            l = s1.length();
        }

        if (!this.text.isEmpty() && j < this.text.length()) {
            s = s + this.text.substring(j);
        }

        if (this.validator.test(s)) {
            this.text = s;
            this.setCursorPos(i + l);
            this.setSelectionPos(this.cursorPosition);
            this.triggerResponder(this.text);
        }
    }


    protected void triggerResponder(String p_212951_1_) {
        if (this.guiResponder != null) {
            this.guiResponder.accept(p_212951_1_);
        }

        this.nextNarration = Util.getMillis() + 500L;
    }


    private void delete(int p_212950_1_) {
        if (Screen.hasControlDown()) {
            this.deleteWords(p_212950_1_);
        } else {
            this.deleteFromCursor(p_212950_1_);
        }

    }


    /**
     * Deletes the given number of words from the current cursor's position, unless
     * there is currently a selection, in which case the selection is deleted
     * instead.
     */
    public void deleteWords(int num) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(num) - this.cursorPosition);
            }
        }
    }


    /**
     * Deletes the given number of characters from the current cursor's position,
     * unless there is currently a selection, in which case the selection is deleted
     * instead.
     */
    public void deleteFromCursor(int num) {
        if (this.text.isEmpty())
            return;

        if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
        } else {
            boolean flag = num < 0;
            int i = flag ? this.cursorPosition + num : this.cursorPosition;
            int j = flag ? this.cursorPosition : this.cursorPosition + num;
            String s = "";
            if (i >= 0) {
                s = this.text.substring(0, i);
            }

            if (j < this.text.length()) {
                s = s + this.text.substring(j);
            }

            if (this.validator.test(s)) {
                this.text = s;
                if (flag) {
                    this.moveCursorBy(num);
                }

                this.triggerResponder(this.text);
            }
        }
    }


    /**
     * Gets the starting index of the word at the specified number of words away
     * from the cursor position.
     */
    public int getNthWordFromCursor(int numWords) {
        return this.getNthWordFromPos(numWords, this.getCursorPosition());
    }


    /**
     * Gets the starting index of the word at a distance of the specified number of
     * words away from the given position.
     */
    private int getNthWordFromPos(int n, int pos) {
        return this.getNthWordFromPosWS(n, pos, true);
    }


    /**
     * Like getNthWordFromPos (which wraps this), but adds option for skipping
     * consecutive spaces
     */
    private int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
        int i = pos;
        boolean flag = n < 0;
        int j = Math.abs(n);

        for (int k = 0; k < j; ++k) {
            if (!flag) {
                int l = this.text.length();
                i = this.text.indexOf(32, i);
                if (i == -1) {
                    i = l;
                } else {
                    while (skipWs && i < l && this.text.charAt(i) == ' ') {
                        ++i;
                    }
                }
            } else {
                while (skipWs && i > 0 && this.text.charAt(i - 1) == ' ') {
                    --i;
                }

                while (i > 0 && this.text.charAt(i - 1) != ' ') {
                    --i;
                }
            }
        }

        return i;
    }


    /**
     * Moves the text cursor by a specified number of characters and clears the
     * selection
     */
    public void moveCursorBy(int num) {
        this.setCursorPosition(this.cursorPosition + num);
    }


    /**
     * Sets the current position of the cursor.
     */
    public void setCursorPosition(int pos) {
        this.setCursorPos(pos);
        if (!this.shiftDown) {
            this.setSelectionPos(this.cursorPosition);
        }

        this.triggerResponder(this.text);
    }


    public void setCursorPos(int pos) {
        this.cursorPosition = MathHelper.clamp(pos, 0, this.text.length());
    }


    /**
     * Moves the cursor to the very start of this text box.
     */
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }


    /**
     * Moves the cursor to the very end of this text box.
     */
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }


    @SuppressWarnings("resource")
    @Override
    // TODO replace hardcoded key ints
    public boolean keyPressed(int key1, int key2, int key3) {
        if (!this.getActive()) {
            return false;
        } else {
            this.shiftDown = Screen.hasShiftDown();
            if (Screen.isSelectAll(key1)) {
                this.setCursorPositionEnd();
                this.setSelectionPos(0);
                return true;
            } else if (Screen.isCopy(key1)) {
                Minecraft.getInstance().keyboardHandler.setClipboard(this.getSelectedText());
                return true;
            } else if (Screen.isPaste(key1)) {
                if (this.isEnabled) {
                    this.writeText(Minecraft.getInstance().keyboardHandler.getClipboard());
                }

                return true;
            } else if (Screen.isCut(key1)) {
                Minecraft.getInstance().keyboardHandler.setClipboard(this.getSelectedText());
                if (this.isEnabled) {
                    this.writeText("");
                }

                return true;
            } else {
                switch (key1) {
                    case 259:
                        if (this.isEnabled) {
                            this.shiftDown = false;
                            this.delete(-1);
                            this.shiftDown = Screen.hasShiftDown();
                        }

                        return true;
                    case 260:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    default:
                        return false;
                    case 261:
                        if (this.isEnabled) {
                            this.shiftDown = false;
                            this.delete(1);
                            this.shiftDown = Screen.hasShiftDown();
                        }

                        return true;
                    case 262:
                        if (Screen.hasControlDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(1));
                        } else {
                            this.moveCursorBy(1);
                        }

                        return true;
                    case 263:
                        if (Screen.hasControlDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(-1));
                        } else {
                            this.moveCursorBy(-1);
                        }

                        return true;
                    case 268:
                        this.setCursorPositionZero();
                        return true;
                    case 269:
                        this.setCursorPositionEnd();
                        return true;
                }
            }
        }
    }


    public boolean getActive() {
        return this.getVisible() && this.isFocused() && this.isEnabled();
    }


    @Override
    public boolean charTyped(char char1, int char2) {
        if (!this.getActive()) {
            return false;
        } else if (SharedConstants.isAllowedChatCharacter(char1)) {
            if (this.isEnabled) {
                this.writeText(Character.toString(char1));
            }

            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!this.getVisible()) {
            return false;
        } else {
            boolean flag = mouseX >= (double) this.x && mouseX < (double) (this.x + this.width) && mouseY >= (double) this.y && mouseY < (double) (this.y + this.height);
            if (this.canLoseFocus) {
                this.setFocused(flag);
            }

            if (this.isFocused() && flag && mouseButton == 0) {
                int i = MathHelper.floor(mouseX) - this.x;
                if (getEnableBackgroundDrawing()) {
                    i -= 4;
                }

                String s = this.fontRenderer.plainSubstrByWidth(this.text.substring(this.lineScrollOffset), this.getAdjustedWidth());
                this.setCursorPosition(this.fontRenderer.plainSubstrByWidth(s, i).length() + this.lineScrollOffset);
                return true;
            } else {
                return false;
            }
        }
    }


    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                if (StyleManager.getCurrentStyle() instanceof StyleVanilla) {
                    fill(matrix, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, StyleManager.getCurrentStyle().getMainColor().getInt());
                    fill(matrix, this.x, this.y, this.x + this.width, this.y + this.height, /* StyleManager.getCurrentStyle() instanceof StyleVanilla ? */ -16777216 /* :ColorUtils.hsvToRGBInt(0, 0, 55).getInt() */);
                } else {
                    GuiUtil.drawFrame(matrix, x, y, x + width, y + height, 1, StyleManager.getCurrentStyle().getMainColor());
                }
            }

            int color = StyleManager.getCurrentStyle().getFGColor(this).getInt();
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String renderString = this.fontRenderer.plainSubstrByWidth(this.text.substring(this.lineScrollOffset), this.getAdjustedWidth());
            boolean flag = j >= 0 && j <= renderString.length();
            boolean flag1 = this.isFocused() && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.getEnableBackgroundDrawing() ? this.x + 4 : this.x;
            int i1 = this.getEnableBackgroundDrawing() ? this.y + (this.height - 8) / 2 : this.y;
            int j1 = l;
            if (k > renderString.length()) {
                k = renderString.length();
            }

            if (!renderString.isEmpty()) {
                String s1 = flag ? renderString.substring(0, j) : renderString;
                j1 = this.fontRenderer.drawShadow(matrix, this.textFormatter.apply(s1, this.lineScrollOffset), (float) l, (float) i1, color);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;
            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (!renderString.isEmpty() && flag && j < renderString.length()) {
                this.fontRenderer.drawShadow(matrix, this.textFormatter.apply(renderString.substring(j), this.cursorPosition), (float) j1, (float) i1, color);
            }

            if (!flag2 && this.suggestion != null) {
                this.fontRenderer.drawShadow(matrix, this.suggestion, (float) (k1 - 1), (float) i1, -8355712);
            }

            if (flag1) {
                if (flag2) {
                    AbstractGui.fill(matrix, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
                } else {
                    this.fontRenderer.drawShadow(matrix, "_", (float) k1, (float) i1, color);
                }
            }

            if (k != j) {
                int l1 = l + this.fontRenderer.width(renderString.substring(0, k));
                this.drawSelectionBox(k1, i1 - 1, l1 - 1, i1 + 1 + 9);
            }

            if(text.length() == 0 && hint != null){
                this.fontRenderer.drawShadow(matrix, hint, (float) j1, (float) i1, color);
            }
        }
    }


    /**
     * Draws the blue selection box.
     */
    private void drawSelectionBox(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        if (startY < endY) {
            int j = startY;
            startY = endY;
            endY = j;
        }

        if (endX > this.x + this.width) {
            endX = this.x + this.width;
        }

        if (startX > this.x + this.width) {
            startX = this.x + this.width;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.vertex((double) startX, (double) endY, 0.0D).endVertex();
        bufferbuilder.vertex((double) endX, (double) endY, 0.0D).endVertex();
        bufferbuilder.vertex((double) endX, (double) startY, 0.0D).endVertex();
        bufferbuilder.vertex((double) startX, (double) startY, 0.0D).endVertex();
        tessellator.end();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }


    /**
     * Sets the maximum length for the text in this text box. If the current text is
     * longer than this length, the current text will be trimmed.
     */
    public void setMaxStringLength(int length) {
        this.maxStringLength = length;
        if (this.text.length() > length) {
            this.text = this.text.substring(0, length);
            this.triggerResponder(this.text);
        }

    }


    /**
     * returns the maximum number of character that can be contained in this textbox
     */
    private int getMaxStringLength() {
        return this.maxStringLength;
    }


    /**
     * returns the current position of the cursor
     */
    public int getCursorPosition() {
        return this.cursorPosition;
    }


    /**
     * Gets whether the background and outline of this text box should be drawn
     * (true if so).
     */
    private boolean getEnableBackgroundDrawing() {
        return true;
        // return !(StyleManager.getCurrentStyle() instanceof StyleSpectrum);
    }


    @Override
    public boolean changeFocus(boolean p_changeFocus_1_) {
        return this.visible && this.isEnabled && super.changeFocus(p_changeFocus_1_);
    }

    @Override
    protected void setFocused(boolean p_230996_1_) {
        super.setFocused(p_230996_1_);
    }

    @Override
    public boolean isMouseOver(double p_isMouseOver_1_, double p_isMouseOver_3_) {
        return this.visible && p_isMouseOver_1_ >= (double) this.x && p_isMouseOver_1_ < (double) (this.x + this.width) && p_isMouseOver_3_ >= (double) this.y && p_isMouseOver_3_ < (double) (this.y + this.height);
    }


    @Override
    protected void onFocusedChanged(boolean p_onFocusedChanged_1_) {
        if (p_onFocusedChanged_1_) {
            this.cursorCounter = 0;
        }

    }


    private boolean isEnabled() {
        return this.isEnabled;
    }


    /**
     * Sets whether this text box is enabled. Disabled text boxes cannot be typed
     * in.
     */
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }


    /**
     * returns the width of the textbox depending on if background drawing is
     * enabled
     */
    public int getAdjustedWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }


    /**
     * Sets the position of the selection anchor (the selection anchor and the
     * cursor position mark the edges of the selection). If the anchor is set beyond
     * the bounds of the current text, it will be put back inside.
     */
    public void setSelectionPos(int position) {
        int i = this.text.length();
        this.selectionEnd = MathHelper.clamp(position, 0, i);
        if (this.fontRenderer != null) {
            if (this.lineScrollOffset > i) {
                this.lineScrollOffset = i;
            }

            int j = this.getAdjustedWidth();
            String s = this.fontRenderer.plainSubstrByWidth(this.text.substring(this.lineScrollOffset), j);
            int k = s.length() + this.lineScrollOffset;
            if (this.selectionEnd == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRenderer.plainSubstrByWidth(this.text, j, true).length();
            }

            if (this.selectionEnd > k) {
                this.lineScrollOffset += this.selectionEnd - k;
            } else if (this.selectionEnd <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - this.selectionEnd;
            }

            this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
        }

    }


    /**
     * Sets whether this text box loses focus when something other than it is
     * clicked.
     */
    public void setCanLoseFocus(boolean canLoseFocusIn) {
        this.canLoseFocus = canLoseFocusIn;
    }


    /**
     * returns true if this textbox is visible
     */
    public boolean getVisible() {
        return this.visible;
    }


    /**
     * Sets whether or not this textbox is visible
     */
    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }


    public void setSuggestion(@Nullable String suggestion) {
        this.suggestion = suggestion;
    }
}
