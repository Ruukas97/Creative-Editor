package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataColor;
import creativeeditor.screen.widgets.HexField;
import creativeeditor.screen.widgets.SliderColorTag;
import creativeeditor.util.CEStringUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class ColorScreen extends ParentItemScreen {
    @Getter
    private final DataColor color;
    @Getter
    @Setter
    private boolean useAlpha;

    private SliderColorTag red, green, blue;
    private HexField hex;

    private boolean draggingSatBrightPicker = false;
    private boolean draggingHuePicker = false;


    public ColorScreen(Screen lastScreen, DataItem editing, DataColor color, boolean useAlpha) {
        super(new TranslationTextComponent("gui.color"), lastScreen, editing);
        setRenderItem(true, 2f);
        this.color = color;
        this.useAlpha = useAlpha;
    }


    public ColorScreen(Screen lastScreen, DataItem editing, DataColor color, int defaultColor, boolean useAlpha) {
        this(lastScreen, editing, color, useAlpha);
        color.setDefColor(defaultColor);
        if (defaultColor != 0 && color.getInt() == 0)
            color.setInt(defaultColor);
    }


    @Override
    protected void init() {
        super.init();
        hex = addButton(new HexField(font, width / 3 + (font.width("#FFFFFF") + 8), (height / 3 + 20 * 2) + 10, 16, color));
        int yStart = this.height / 2 - 50;
        int x = width * 2 / 3 + 16;
        red = addButton(new SliderColorTag(x, yStart, width - 20 - x, 20, color, 0)); // red
        green = addButton(new SliderColorTag(x, yStart + 35, width - 20 - x, 20, color, 1)); // green
        blue = addButton(new SliderColorTag(x, yStart + 70, width - 20 - x, 20, color, 2)); // blue
    }


    @Override
    public void reset(Widget w) {
        color.setInt(color.getDefColor());
    }


    @Override
    public void tick() {
        super.tick();
        hex.updateCursorCounter();
    }


    public boolean setMouseColor(double mouseX, double mouseY) {
        int x = 25;
        int xEnd = width / 3 - 20;
        int width = xEnd - x;
        int y = 60;
        int yEnd = y + width;
        if (draggingSatBrightPicker) {
            mouseX = MathHelper.clamp(mouseX, x, xEnd);
            mouseY = MathHelper.clamp(mouseY, y, yEnd);
            float sat = (float) ((mouseX - (double) x) / (double) width);
            float bri = (float) (1d - (mouseY - 60d) / width);
            color.setHSB(color.getHue(), sat, bri);
            return true;
        } else if (draggingHuePicker) {
            mouseX = MathHelper.clamp(mouseX, x, xEnd);
            //mouseY = MathHelper.clamp(mouseY, yEnd + 3, yEnd + 6);
            float hue = (float) ((mouseX - (double) x) / (double) width);
            // hue %= 1;
            color.setHSB(hue, color.getSaturation(), color.getBrightness());
            return true;
        }
        return false;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            int x = 25;
            int xEnd = width / 3 - 20;
            int width = xEnd - x;
            int y = 60;
            int yEnd = y + width;
            int height = yEnd - y;
            if (GuiUtil.isMouseInRegion(mouseX, mouseY, x, 60, width, height)) {
                draggingSatBrightPicker = true;
            } else if (GuiUtil.isMouseInRegion(mouseX, mouseY, x, yEnd + 3, width, 3)) {
                draggingHuePicker = true;
            }

            if (setMouseColor(mouseX, mouseY))
                return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            draggingSatBrightPicker = false;
            draggingHuePicker = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double onX, double onY) {
        if (setMouseColor(mouseX, mouseY)) {
            red.setColoredValue();
            green.setColoredValue();
            blue.setColoredValue();
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, onX, onY);
    }


    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color guiColor) {
        super.backRender(matrix, mouseX, mouseY, p3, guiColor);

        // First vertical line
        fill(matrix, width / 3, 20, width / 3 + 1, height - 20, guiColor.getInt());
        // Second vertical line
        fill(matrix, width * 2 / 3, 20, width * 2 / 3 + 1, height - 20, guiColor.getInt());
        // Left horizontal line
        fill(matrix, 20, 40, width / 3 - 15, 41, guiColor.getInt());
        // Right horizontal line
        fill(matrix, width * 2 / 3 + 16, 40, width - 20, 41, guiColor.getInt());

        int leftText = (5 + width / 3) / 2;
        drawCenteredString(matrix, font, "Color Pickers", leftText, 30, guiColor.getInt());
        drawCenteredString(matrix, font, "HSB Picker", leftText, 45, guiColor.getInt());

        int rightText = width * 2 / 3 + 16;
        drawCenteredString(matrix, font, "RGB Sliders", rightText + ((width - 20 - rightText) / 2), 30, guiColor.getInt());


        int x = 25;
        int xEnd = width / 3 - 20;
        int pickerWidth = xEnd - x;
        int y = 60;
        int yEnd = y + pickerWidth;
        // int pickerHeight = yEnd - y;
        float hue = color.getHue();

        GuiUtil.fillColorPicker(this, x, 60, xEnd, yEnd, hue);

        y = yEnd + 3;
        yEnd = y + 3;
        // pickerHeight = 3;
        GuiUtil.fillHueSlider(this, x, y, xEnd, yEnd);


        int inverseColor = java.awt.Color.HSBtoRGB((hue + .5f) % 1f, 1f, 1f);

        int colorX = (int) (x + (color.getSaturation() * pickerWidth));
        int colorY = (int) (60 + ((1 - color.getBrightness()) * pickerWidth));


        fill(matrix, colorX - 1, colorY - 1, colorX, colorY, inverseColor);
        fill(matrix, colorX + 1, colorY - 1, colorX + 2, colorY, inverseColor);
        fill(matrix, colorX - 1, colorY + 1, colorX, colorY + 2, inverseColor);
        fill(matrix, colorX + 1, colorY + 1, colorX + 2, colorY + 2, inverseColor);

        int hueX = (int) (x + color.getHue() * pickerWidth);
        fill(matrix, hueX, y - 1, hueX + 1, y, inverseColor);
        fill(matrix, hueX, yEnd, hueX + 1, yEnd + 1, inverseColor);
    }


    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color guiColor) {
        super.mainRender(matrix, mouseX, mouseY, p3, guiColor);
        int halfWidth = width / 2;
        int yStart = height / 3 + 15;
        Color color = this.color;
        int i = 0;
        drawCenteredString(matrix, font, I18n.get("gui.color.rgb", CEStringUtils.zeroPaddedInt(color.getRed(), 3), CEStringUtils.zeroPaddedInt(color.getGreen(), 3), CEStringUtils.zeroPaddedInt(color.getBlue(), 3)), halfWidth, yStart + (20 * i++), guiColor.getInt());
        drawCenteredString(matrix, font, I18n.get("gui.color.hex", color.getHexString()), halfWidth, yStart + (20 * i++), guiColor.getInt());
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color guiColor) {
        super.overlayRender(matrix, mouseX, mouseY, p3, guiColor);
        GuiUtil.addToolTip(matrix, this, resetButton, mouseX, mouseY, I18n.get("gui.color.reset"));
    }
}
