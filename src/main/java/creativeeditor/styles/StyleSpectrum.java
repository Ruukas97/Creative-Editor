package creativeeditor.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;

public class StyleSpectrum implements Style {
    private int spectrumTick = 0;
    private static final Color color = new Color( 0xFFFF0000 );
    private static final Color inactive = new Color( 0xFFDD00DD );
    private static final Color hovered = new Color( 0xFFFF9900 );


    @Override
    public Color getMainColor() {
        return color.copy();
    }


    @Override
    public void update() {
        spectrumTick++;
        spectrumTick %= 5;
        if (spectrumTick == 0) {
            color.hueShift();
            inactive.hueShift();
            hovered.hueShift();
        }
    }


    @Override
    public void renderBackground( ParentScreen screen ) {
        GuiUtil.fillVerticalGradient( screen, 0, 0, screen.width, screen.height, -1072689136, -804253680 );
    }


    @Override
    public void renderButton( IStyledWidget button, int mouseX, int mouseY, float alpha ) {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer font = mc.fontRenderer;
        int j = getFGColor( button.getWidget() ).getInt();
        GuiUtil.drawFrame( button.getWidget().x, button.getWidget().y, button.getWidget().x + button.getWidget().getWidth(), button.getWidget().y + button.getWidget().getHeight(), 1, new Color( j ) );
        button.renderBg( mc, mouseX, mouseY );

        String buttonText = button.getWidget().getMessage();
        int strWidth = mc.fontRenderer.getStringWidth( buttonText );
        int ellipsisWidth = mc.fontRenderer.getStringWidth( "..." );

        if (strWidth > button.getWidget().getWidth() - 6 && strWidth > ellipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth( buttonText, button.getWidget().getWidth() - 6 - ellipsisWidth ).trim() + "...";

        button.getWidget().drawCenteredString( font, buttonText, button.getWidget().x + button.getWidget().getWidth() / 2, button.getWidget().y + (button.getWidget().getHeight() - 8) / 2, j | MathHelper.ceil( alpha * 255.0F ) << 24 );
        button.getWidget().drawCenteredString( font, buttonText, button.getWidget().x + button.getWidget().getWidth() / 2, button.getWidget().y + (button.getWidget().getHeight() - 8) / 2, getMainColor().getInt() );
    }


    @Override
    public void renderSlider( IStyledSlider<?> slider, int mouseX, int mouseY ) {
        int x = slider.getWidget().x + 1 + (int) ((slider.getWidget().getWidth() - 3) * (slider.getValue().floatValue() - slider.getMin().floatValue()) / (slider.getMax().floatValue() - slider.getMin().floatValue()));
        AbstractGui.fill( x, slider.getWidget().y + 3, x + 1, slider.getWidget().y + slider.getWidget().getHeight() - 3, getMainColor().getInt() );
    }


    @Override
    public Color getFGColor( Widget widget ) {
        return getFGColor( widget.active, widget.isHovered() );
    }


    @Override
    public Color getFGColor( boolean active, boolean hovered ) {
        if (!active)
            return inactive.copy();
        if (hovered)
            return StyleSpectrum.hovered.copy();
        return getMainColor();
    }
}
