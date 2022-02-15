package infinityitemeditor.styles;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.components.AbstractWidget;

public interface Style {
    Color getMainColor();


    Color getFGColor(AbstractWidget widget);


    Color getFGColor(boolean active, boolean hovered);


    void update();


    void renderBackground(PoseStack poseStack, ParentScreen screen);


    void renderButton(PoseStack poseStack, IStyledWidget button, int mouseX, int mouseY, float alpha);


    void renderSlider(PoseStack poseStack, IStyledSlider<?> slider, int mouseX, int mouseY);
}
