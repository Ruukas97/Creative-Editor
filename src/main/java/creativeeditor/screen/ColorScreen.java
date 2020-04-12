package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataColor;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.widgets.StyledSlider;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class ColorScreen extends ParentItemScreen {
    @Getter
    private DataColor color;
    @Getter
    @Setter
    private boolean useAlpha;

    private StyledSlider red, green, blue, hue, saturation, brightness, alpha;
    // private int[][] pixels = new int[256][256];

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
            color.get().setInt( defaultColor );
    }


    @Override
    protected void init() {
        super.init();
    }


    @Override
    public void reset( Widget w ) {
        color.get().setInt( color.getDefColor() );
    }


    public boolean setMouseColor( double mouseX, double mouseY ) {
        int xPos = 25;
        int xEnd = width / 3 - 20;
        int width = xEnd - xPos;
        if (draggingSatBrightPicker && GuiUtil.isMouseInRegion( mouseX, mouseY, xPos, 60, width + 1, 100 )) {
            double sat = (mouseX - (double) xPos) / (double) width;
            double bri = 1d - (mouseY - 60d) / 100d;
            // color.set( ColorUtils.hsvToRGB(0f, (float) sat, (float) bri ) );
            return true;
        }
        if (draggingHuePicker && GuiUtil.isMouseInRegion( mouseX, mouseY, xPos, 163, width, 3 )) {
            double hue = (mouseX - (double) xPos) / (double) width;
            // color.get().setHue( (int) (hue * 255) );
            return true;
        }
        return false;
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        if (mouseButton == 0) {
            int xPos = 25;
            int xEnd = width / 3 - 20;
            int width = xEnd - xPos;
            if (GuiUtil.isMouseInRegion( mouseX, mouseY, xPos, 60, width, 100 )) {
                draggingSatBrightPicker = true;
            }
            else if (GuiUtil.isMouseInRegion( mouseX, mouseY, xPos, 163, width, 3 )) {
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
        guiColor = color.get();
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
        drawCenteredString( font, "HSV Picker", leftText, 45, guiColor.getInt() );


        int x = 25;
        int xEnd = width / 3 - 20;
        int width = xEnd - x;
        int y = 60;
        int yEnd = y + width;
        int height = yEnd - y;

        GuiUtil.fillColorPicker( this, x, 60, xEnd, yEnd, color.get().getHue() / 255f );
        
        y = yEnd + 3;
        yEnd = y+3;
        height = 3;
        GuiUtil.fillHueSlider( this, x, y, xEnd, yEnd );


        // double hue = (mouseX - (double) xPos) / (double) width;

        int colorX = (int) (x + (color.get().getSaturation() / 255f) * width);
        int colorY = (int) (160 - color.get().getValue() * height);

        Color c = color.get();
        float[] hsv = java.awt.Color.RGBtoHSB( c.getRed(), c.getGreen(), c.getBlue(), null );
        int inverseColor = java.awt.Color.HSBtoRGB( (hsv[0] + .5f) % 1f, hsv[1], hsv[2] );
        // GuiUtil.drawFrame( colorX - 1, colorY - 1, colorX + 2, colorY + 2, 1, new
        // Color( inverseColor ) );

        fill( colorX - 1, colorY - 1, colorX, colorY, inverseColor );
        fill( colorX + 1, colorY - 1, colorX + 2, colorY, inverseColor );
        fill( colorX - 1, colorY + 1, colorX, colorY + 2, inverseColor );
        fill( colorX + 1, colorY + 1, colorX + 2, colorY + 2, inverseColor );

        int hueX = (int) (x + (color.get().getHue() / 255f) * width);
        fill( hueX, 162, hueX + 1, 163, inverseColor );
        fill( hueX, 166, hueX + 1, 167, inverseColor );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color guiColor ) {
        guiColor = color.get();
        super.mainRender( mouseX, mouseY, p3, guiColor );
        int halfWidth = width / 2;
        Color color = this.color.get();
        int i = 0;
        drawCenteredString( font, I18n.format( "gui.color.rgb", color.getRed(), color.getGreen(), color.getBlue() ), halfWidth, 35 + (20 * i++), color.getInt() );
        drawCenteredString( font, I18n.format( "gui.color.hsv", color.getHue(), color.getSaturation(), color.getValue() ), halfWidth, 35 + (20 * i++), color.getInt() );
        if (useAlpha) {
            drawCenteredString( font, I18n.format( "gui.color.alpha", color.getAlpha() ), halfWidth, 35 + (20 * i++), color.getInt() );
        }
        drawCenteredString( font, I18n.format( "gui.color.hex", color.getHexString() ), halfWidth, 35 + (20 * i++), color.getInt() );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color guiColor ) {
        guiColor = color.get();
        super.overlayRender( mouseX, mouseY, p3, guiColor );
        GuiUtil.addToolTip( this, resetButton, mouseX, mouseY, I18n.format( "gui.color.reset" ) );
    }
}
