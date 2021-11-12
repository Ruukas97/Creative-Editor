package infinityitemeditor.styles;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.widget.Widget;

public interface Style {
    Color getMainColor();


    Color getFGColor(Widget widget);


    Color getFGColor(boolean active, boolean hovered);


    void update();


    void renderBackground(MatrixStack matrix, ParentScreen screen);


    void renderButton(MatrixStack matrix, IStyledWidget button, int mouseX, int mouseY, float alpha);


    void renderSlider(MatrixStack matrix, IStyledSlider<?> slider, int mouseX, int mouseY);
}
