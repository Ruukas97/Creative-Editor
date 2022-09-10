package infinityitemeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.base.DataUUID;
import infinityitemeditor.styles.Style;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class UUIDField extends Widget {
    private final FontRenderer fontRenderer;

    @Getter
    DataUUID dataUUID;
    public char[] digits;

    private final char[] allowed = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f'};

    private int cursorCounter;

    private final boolean enableBackgroundDrawing = true;
    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    private boolean canLoseFocus = true;

    @Getter
    private final boolean enabled = true;

    private int cursorPosition;


    public UUIDField(FontRenderer font, int x, int y, int height, DataUUID uuid) {
        super(x, y, font.width("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF") + 8, height, new StringTextComponent(""));
        this.dataUUID = uuid;
        this.fontRenderer = font;

        this.digits = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
        setValue(dataUUID.getData());
    }


    public static int getMax() {
        return 0xFFFFFF;
    }


    public static int getMin() {
        return 0;
    }


    public String getHexString() {
        return dataUUID.getData().toString();
    }


    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter() {
        this.cursorCounter++;
    }


    /**
     * Sets the text of the textbox, and moves the cursor to the end.
     */
    public void setDigit(int i, char c) {
        if (isAllowed(c)) {
            digits[i] = c;
        }
        UUID value = getUUIDValue();
        dataUUID.getMostSignificantBits().set(value.getMostSignificantBits());
        dataUUID.getLeastSignificantBits().set(value.getLeastSignificantBits());
    }


    /**
     * Sets the text of the textbox, and moves the cursor to the end.
     */
    public void setDigit(char c) {
        this.setDigit(cursorPosition, c);
    }


    private void setValue(UUID uuid) {
        String s = uuid.toString().replaceAll("-", "");
        for (int i = 0; i < s.length(); i++) {
            this.digits[i] = s.charAt(i);
        }
    }

    private String getStringWithHyphens() {
        String without = new String(digits);
        return without.substring(0, 8) + '-' + without.substring(8, 12) + '-' + without.substring(12, 16) + '-' + without.substring(16, 20) + '-' + without.substring(20);
    }

    private UUID getUUIDValue() {
        try {
            return UUID.fromString(getStringWithHyphens());
        } catch (Exception e) {
            e.printStackTrace();
            return new UUID(0, 0);
        }
    }


    public boolean isAllowed(char c) {
        for (char ch : allowed) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }


    /**
     * Moves the text cursor by a specified number of characters and clears the
     * selection
     */
    public void moveCursor(boolean right) {
        this.setCursorPosition(this.cursorPosition + (right ? 1 : -1));
    }


    /**
     * Sets the current position of the cursor.
     */
    public void setCursorPosition(int pos) {
        this.cursorPosition = pos;
        int i = digits.length - 1;
        this.cursorPosition = MathHelper.clamp(this.cursorPosition, 0, i);
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
        this.setCursorPosition(digits.length - 1);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        Minecraft mc = Minecraft.getInstance();
        if (Screen.isCopy(keyCode)) {
            mc.keyboardHandler.setClipboard(String.valueOf(digits));
            return true;
        }
        if (Screen.isPaste(keyCode)) {
            try {
                UUID value = UUID.fromString(mc.keyboardHandler.getClipboard().replaceAll("-", ""));
                dataUUID.getMostSignificantBits().set(value.getMostSignificantBits());
                dataUUID.getLeastSignificantBits().set(value.getLeastSignificantBits());
                return true;
            } catch (IllegalArgumentException e) {
            }
        }
        if (Screen.isCut(keyCode)) {
            mc.keyboardHandler.setClipboard(String.valueOf(digits));
            return true;
        }

        switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT:
                moveCursor(false);
                return true;
            case GLFW.GLFW_KEY_RIGHT:
                moveCursor(true);
                return true;
            default:
                return super.keyPressed(keyCode, scanCode, modifier);
        }
    }


    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        if (!this.isActive()) {
            return false;
        } else if (isAllowed(typedChar)) {
            if (this.isEnabled()) {
                setDigit(Character.toUpperCase(typedChar));
                moveCursor(true);
            }

            return true;
        } else {
            return false;
        }
    }


    public boolean isActive() {
        return this.visible && this.isFocused() && this.isEnabled();
    }


    @Override
    public boolean changeFocus(boolean p_changeFocus_1_) {
        return this.visible && this.isEnabled() && super.changeFocus(p_changeFocus_1_);
    }


    @Override
    protected void onFocusedChanged(boolean p_onFocusedChanged_1_) {
        if (p_onFocusedChanged_1_) {
            this.cursorCounter = 0;
        }

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        boolean flag = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;

        if (this.canLoseFocus) {
            this.setFocused(flag);
        }

        if (this.isFocused() && flag && mouseButton == 0) {
            int i = (int) mouseX - this.x;

            if (this.enableBackgroundDrawing) {
                i -= 4;
            }
            String s = this.fontRenderer.plainSubstrByWidth(this.getStringWithHyphens(), this.getWidth());
            String found = this.fontRenderer.plainSubstrByWidth(s, i);

            this.setCursorPosition((int) (found.length() - found.chars().filter(value -> value == '-').count()));
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void renderButton(MatrixStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Style style = StyleManager.getCurrentStyle();
        Color color = style.getFGColor(this);

        if (!getUUIDValue().equals(dataUUID.getData())) {
            setValue(dataUUID.getData());
        }

        if (this.getEnableBackgroundDrawing()) {
            GuiUtil.drawFrame(matrix, x, y, x + width, y + height, 1, color);
        }

        int cursorPos = this.cursorPosition;
        String withHyphens = getStringWithHyphens();
        String string = this.fontRenderer.plainSubstrByWidth(withHyphens, this.getWidth());
        boolean cursorFine = cursorPos >= 0 && cursorPos <= string.length();
        boolean displayCursor = isFocused() && this.cursorCounter / 6 % 2 == 0 && cursorFine;
        int textX = this.enableBackgroundDrawing ? this.x + 4 : this.x;
        int textY = this.enableBackgroundDrawing ? this.y + (this.height - 8) / 2 : this.y;
        int halfX = textX;

        if (!string.isEmpty()) {
            String halfString = cursorFine ? string.substring(0, cursorPos) : string;
            halfX = this.fontRenderer.drawShadow(matrix, halfString, (float) textX, (float) textY, color.getInt()) - 1;
        }

        int hyphens;
        if (cursorPos >= 20) {
            hyphens = 4;
        } else if (cursorPos >= 16) {
            hyphens = 3;
        } else if (cursorPos >= 12) {
            hyphens = 2;
        } else if (cursorPos >= 8) {
            hyphens = 1;
        } else {
            hyphens = 0;
        }

        int cursorX;
        if (cursorFine) {
            cursorX = halfX + fontRenderer.width("-") * hyphens;
        } else {
            cursorX = textX + this.width;
        }

        if (!string.isEmpty() && cursorFine && cursorPos < string.length()) {
            halfX = this.fontRenderer.drawShadow(matrix, string.substring(cursorPos), (float) halfX, (float) textY, color.getInt());
        }

        if (displayCursor) {
            this.fontRenderer.drawShadow(matrix, "_", (float) cursorX, (float) textY, color.getInt());
        }
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
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }


    /**
     * Sets focus to this gui element
     */
    @Override
    public void setFocused(boolean isFocusedIn) {
        super.setFocused(isFocusedIn);
        if (isFocusedIn) {
            this.cursorCounter = 0;
        }
    }


    /**
     * returns the width of the textbox depending on if background drawing is
     * enabled
     */
    @Override
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 6 : this.width;
    }


    /**
     * Sets whether this text box loses focus when something other than it is
     * clicked.
     */
    public void setCanLoseFocus(boolean canLoseFocusIn) {
        this.canLoseFocus = canLoseFocusIn;
    }
}
