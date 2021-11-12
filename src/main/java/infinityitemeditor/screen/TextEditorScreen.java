package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class TextEditorScreen extends ParentScreen {
    //private boolean plainText;
    //private boolean multiLine;
    private int cursor = 0;
    //private int selectionEnd = cursor;
    private final DataString text;
    private int cursorTicks = 0;
    private int preview = 0;


    public TextEditorScreen(Screen lastScreen) {
        this(lastScreen, new DataString(), true, true);
    }


    public TextEditorScreen(Screen lastScreen, DataString text, boolean plainText, boolean multiLine) {
        super(new TranslationTextComponent("gui.texteditor"), lastScreen);
        //this.plainText = plainText;
        //this.multiLine = multiLine;
        this.text = text;
    }


    @Override
    protected void init() {
        super.init();

        addButton(new StyledButton(15, 15, 60, 10, I18n.get("gui.texteditor.preview"), b -> {
            preview = (preview + 1) % 3;
        }));

        minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }


    @Override
    public void removed() {
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }


    @Override
    public void tick() {
        super.tick();
        cursorTicks = (cursorTicks + 1) % 20;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);

    }


    @Override
    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
    }


    @Override
    public boolean keyPressed(int keyCode, int scan, int modifier) {
        if (super.keyPressed(keyCode, scan, modifier))
            return true;

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE && text.get().length() > 0) {
            String s = text.get();
            text.set(s.substring(0, cursor - 1) + s.substring(cursor));
            cursor--;
            cursorTicks = 0;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_DELETE && text.get().length() > cursor) {
            String s = text.get();
            text.set(s.substring(0, cursor) + s.substring(cursor + 1));
            cursorTicks = 0;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            text.set(text.get() + '\n');
            cursor++;
            cursorTicks = 0;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_LEFT && text.get().length() > 0) {
            cursor--;
            cursorTicks = 0;
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_RIGHT && text.get().length() > cursor) {
            cursor++;
            cursorTicks = 0;
            return true;
        }

        return false;
    }


    @Override
    public boolean charTyped(char key, int modifier) {
        if (super.charTyped(key, modifier))
            return true;
        if (key == 167 || SharedConstants.isAllowedChatCharacter(key)) {
            String s = text.get();
            text.set(s.substring(0, cursor) + key + s.substring(cursor));
            cursor++;
            cursorTicks = 0;
            return true;
        }
        return false;
    }


    public String[] getLines() {
        String[] lines = text.get().split("\n");
        return lines;
    }


    public List<String> getWrappedLines() {
        String[] lines = getLines();
        List<String> wrapped = new ArrayList<>();
        for (String line : lines) {
//            wrapped.addAll( Arrays.asList( font.wrapFormattedStringToWidth( line, width - 30 ).split( "\n" ) ) );
        }

        return wrapped;
    }


    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);

        int i = 0;
        int chars = 0;
        for (String line : getWrappedLines()) {
            int x = 15;
            int y = 30 + i * 10;
            String renderLine = line.replace("" + (char) 167, "&");
            drawString(matrix, font, renderLine, x, y, -1);
            if (cursorTicks < 10 && chars + renderLine.length() >= cursor) {
                if (cursor - chars > 0) {
                    x += font.width(renderLine.substring(0, cursor - chars));
                }
                y += 1;
                fill(matrix, x, y, x + 1, y + 8, -1);
            }
            chars += renderLine.length() + 1;
            i++;
        }

        if (cursorTicks < 10) {
            char[] charArray = text.get().toCharArray();
            boolean newLine = false;
            for (int j = charArray.length - 1; j >= 0; j--) {
                char c = charArray[j];
                if (c == '\n') {
                    i++;
                    newLine = true;
                } else
                    break;
            }
            if (newLine) {
                int x = 15;
                int y = 20 + i * 10;
                fill(matrix, x, y, x + 1, y + 8, -1);
            }
        }
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
        if (preview == 1) {
            GuiUtil.addToolTip(matrix, this, mouseX, mouseY, getLines());
        }
    }
}
