package creativeeditor.styles;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;

public interface IStyledWidget {
    Widget getWidget();


    int getYImage(boolean b);


    int getBlitOffset();


    void setHovered(boolean b);


    void renderBackground(MatrixStack matrix, Minecraft mc, int mouseX, int mouseY);
}
