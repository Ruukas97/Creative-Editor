package creativeeditor.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.widget.Widget;

public interface Style {
    Color getMainColor();


    Color getFGColor(Widget widget);


    Color getFGColor(boolean active, boolean hovered);


    void update();


    void renderBackground(ParentScreen screen);


    void renderButton(IStyledWidget button, int mouseX, int mouseY, float alpha);


    void renderSlider(IStyledSlider<?> slider, int mouseX, int mouseY);
}
