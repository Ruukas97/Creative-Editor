package infinityitemeditor.styles;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;

public interface IStyledWidget {
    AbstractWidget getWidget();


    int getImageY(boolean b);


    int getOffsetBlit();


    void setHovered(boolean b);


    void renderBackground(PoseStack poseStack, Minecraft mc, int mouseX, int mouseY);
}
