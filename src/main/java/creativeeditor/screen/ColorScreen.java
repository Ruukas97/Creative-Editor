package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataColor;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.widgets.HexField;
import creativeeditor.widgets.StyledSlider;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class ColorScreen extends ParentItemScreen {
    @Getter
    private DataColor color;
    @Getter
    @Setter
    private boolean useAlpha;

    private StyledSlider red, green, blue, hue, saturation, brightness, alpha;
    private HexField hex;

    private boolean draggingSatBrightPicker = false;
    private boolean draggingHuePicker = false;


    public ColorScreen(Screen lastScreen, DataItem editing, DataColor color, boolean useAlpha) {
        super( new TranslationTextComponent( "gui.color" ), lastScreen, editing );
        setRenderItem( true, 2f );
        this.color = color;
        this.useAlpha = useAlpha;
    }


    public ColorScreen(Screen lastScreen, DataItem editing, DataColor color, int defaultColor, boolean useAlpha) {
        this( lastScreen, editing, color, useAlpha );
        color.setDefColor( defaultColor );
        if (defaultColor != 0 && color.getInt() == 0)
            color.setInt( defaultColor );
    }


    @Override
    protected void init() {
        super.init();
        hex = addButton( new HexField( font, width / 3 + 11, 100, 16, color ) );
    }


    @Override
    public void reset( Widget w ) {
        color.setInt( color.getDefColor() );
    }


    @Override
    public void tick() {
        super.tick();
        hex.updateCursorCounter();
    }


    public boolean setMouseColor( double mouseX, double mouseY ) {
        int x = 25;
        int xEnd = width / 3 - 20;
        int width = xEnd - x;
        int y = 60;
        int yEnd = y + width;
        if (draggingSatBrightPicker) {
            mouseX = MathHelper.clamp( mouseX, x, xEnd );
            mouseY = MathHelper.clamp( mouseY, y, yEnd );
            float sat = (float) ((mouseX - (double) x) / (double) width);
            float bri = (float) (1d - (mouseY - 60d) / width);
            color.setHSB( color.getHue(), sat, bri );
            return true;
        }
        else if (draggingHuePicker) {
            mouseX = MathHelper.clamp( mouseX, x, xEnd );
            mouseY = MathHelper.clamp( mouseY, yEnd + 3, yEnd + 6 );
            float hue = (float) ((mouseX - (double) x) / (double) width);
            // hue %= 1;
            color.setHSB( hue, color.getSaturation(), color.getBrightness() );
            return true;
        }
        return false;
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        if (mouseButton == 0) {
            int x = 25;
            int xEnd = width / 3 - 20;
            int width = xEnd - x;
            int y = 60;
            int yEnd = y + width;
            int height = yEnd - y;
            if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, 60, width, height )) {
                draggingSatBrightPicker = true;
            }
            else if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, yEnd + 3, width, 3 )) {
                draggingHuePicker = true;
            }

            if (setMouseColor( mouseX, mouseY ))
                return true;
        }
        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public boolean mouseReleased( double mouseX, double mouseY, int button ) {
        if (button == 0) {
            draggingSatBrightPicker = false;
            draggingHuePicker = false;
        }
        return super.mouseReleased( mouseX, mouseY, button );
    }


    @Override
    public boolean mouseDragged( double mouseX, double mouseY, int button, double onX, double onY ) {
        if (setMouseColor( mouseX, mouseY )) {
            return true;
        }
        return super.mouseDragged( mouseX, mouseY, button, onX, onY );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color guiColor ) {
        guiColor = color;
        super.backRender( mouseX, mouseY, p3, guiColor );

        // First vertical line
        fill( width / 3, 20, width / 3 + 1, height - 20, guiColor.getInt() );
        // Second vertical line
        fill( width * 2 / 3, 20, width * 2 / 3 + 1, height - 20, guiColor.getInt() );
        // Left horizontal line
        fill( 20, 40, width / 3 - 15, 41, guiColor.getInt() );
        // Right horizontal line
        fill( width * 2 / 3 + 16, 40, width - 20, 41, guiColor.getInt() );

        int leftText = (5 + width / 3) / 2;
        drawCenteredString( font, "Color Pickers", leftText, 30, guiColor.getInt() );
        drawCenteredString( font, "HSB Picker", leftText, 45, guiColor.getInt() );


        int x = 25;
        int xEnd = width / 3 - 20;
        int pickerWidth = xEnd - x;
        int y = 60;
        int yEnd = y + pickerWidth;
        // int pickerHeight = yEnd - y;
        float hue = color.getHue();

        GuiUtil.fillColorPicker( this, x, 60, xEnd, yEnd, hue );

        y = yEnd + 3;
        yEnd = y + 3;
        // pickerHeight = 3;
        GuiUtil.fillHueSlider( this, x, y, xEnd, yEnd );


        int inverseColor = java.awt.Color.HSBtoRGB( (hue + .5f) % 1f, 1f, 1f );

        int colorX = (int) (x + (color.getSaturation() * pickerWidth));
        int colorY = (int) (60 + ((1 - color.getBrightness()) * pickerWidth));


        fill( colorX - 1, colorY - 1, colorX, colorY, inverseColor );
        fill( colorX + 1, colorY - 1, colorX + 2, colorY, inverseColor );
        fill( colorX - 1, colorY + 1, colorX, colorY + 2, inverseColor );
        fill( colorX + 1, colorY + 1, colorX + 2, colorY + 2, inverseColor );

        int hueX = (int) (x + color.getHue() * pickerWidth);
        fill( hueX, y - 1, hueX + 1, y, inverseColor );
        fill( hueX, yEnd, hueX + 1, yEnd + 1, inverseColor );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color guiColor ) {
        guiColor = color;
        super.mainRender( mouseX, mouseY, p3, guiColor );
        int halfWidth = width / 2;
        Color color = this.color;
        int i = 0;
        drawCenteredString( font, I18n.format( "gui.color.rgb", color.getRed(), color.getGreen(), color.getBlue() ), halfWidth, 35 + (20 * i++), color.getInt() );
        float[] hsb = java.awt.Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
        drawCenteredString( font, I18n.format( "gui.color.hsb", hsb[0], hsb[1], hsb[2] ), halfWidth, 35 + (20 * i++), color.getInt() );
        // Color color2 = MathHelper.hsvToRGB( hue, saturation, value );
        drawCenteredString( font, I18n.format( "gui.color.hsv", color.getHue(), color.getHSVSaturation(), color.getValue() ), halfWidth, 35 + (20 * i++), color.getInt() );
        if (useAlpha) {
            drawCenteredString( font, I18n.format( "gui.color.alpha", color.getAlpha() ), halfWidth, 35 + (20 * i++), color.getInt() );
        }
        drawCenteredString( font, I18n.format( "gui.color.hex", color.getHexString() ), halfWidth, 35 + (20 * i++), color.getInt() );
        drawCenteredString( font, I18n.format( "gui.color.dec", (color.getInt() & 0xFFFFFF) ), halfWidth, 35 + (20 * i++), color.getInt() );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color guiColor ) {
        guiColor = color;
        super.overlayRender( mouseX, mouseY, p3, guiColor );
        GuiUtil.addToolTip( this, resetButton, mouseX, mouseY, I18n.format( "gui.color.reset" ) );
    }
}
