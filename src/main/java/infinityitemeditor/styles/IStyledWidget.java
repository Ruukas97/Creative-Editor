package infinityitemeditor.styles;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;

public interface IStyledWidget {
    Widget getWidget();


    int getImageY(boolean b);


    int getOffsetBlit();


    void setHovered(boolean b);


    void renderBackground(MatrixStack matrix, Minecraft mc, int mouseX, int mouseY);
}
