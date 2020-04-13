package creativeeditor.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.widget.Widget;

public interface Style {
    public Color getMainColor();


    public Color getFGColor( Widget widget );


    public Color getFGColor( boolean active, boolean hovered );


    public void update();


    public void renderBackground( ParentScreen screen );


    public void renderButton( IStyledWidget button, int mouseX, int mouseY, float alpha );


    public void renderSlider( IStyledSlider<?> slider, int mouseX, int mouseY );
}
