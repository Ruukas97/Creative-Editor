package creativeeditor.styles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;

public interface IStyledWidget {
    Widget getWidget();


    int getYImage(boolean b);


    int getBlitOffset();


    void setHovered(boolean b);


    void renderBg(Minecraft mc, int mouseX, int mouseY);
}
