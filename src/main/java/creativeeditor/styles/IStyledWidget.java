package creativeeditor.styles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;

public interface IStyledWidget {
    public Widget getWidget();


    public int getYImage( boolean b );


    public int getBlitOffset();


    public void setHovered( boolean b );


    public void renderBg( Minecraft mc, int mouseX, int mouseY );
}
