package infinityitemeditor.styles;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;

public class StyleVanilla extends StyleBase {
    public StyleVanilla() {
        super(new StaticButtonColor(14737632, 16777120, 10526880));
    }


    private final Color mainColor = new Color(255, 200, 200, 200);


    @Override
    public Color getMainColor() {
        return mainColor;
    }


    @Override
    public void renderBackground(PoseStack poseStack, ParentScreen screen) {
        screen.renderBackground(matrix);
    }


    @Override
    public void renderButton(PoseStack poseStack, IStyledWidget button, int mouseX, int mouseY, float alpha) {
        AbstractWidget widget = button.getWidget();
        if (!widget.visible)
            return;

        Minecraft mc = Minecraft.getInstance();
        button.setHovered(mouseX >= widget.x && mouseY >= widget.y && mouseX < widget.x + widget.getWidth() && mouseY < widget.y + widget.getHeight());
        int k = button.getImageY(widget.isHoveredOrFocused());
        GuiUtil.drawContinuousTexturedBox(AbstractWidget.WIDGETS_LOCATION, widget.x, widget.y, 0, 46 + k * 20, widget.getWidth(), widget.getHeight(), 200, 20, 2, 3, 2, 2, button.getOffsetBlit());
        button.renderBackground(poseStack,mc, mouseX, mouseY);

        int color = getFGColor(widget).getInt();

        String buttonText = widget.getMessage().getString();
        int strWidth = mc.font.width(buttonText);
        int ellipsisWidth = mc.font.width("...");

        if (strWidth > widget.getWidth() - 6 && strWidth > ellipsisWidth)
            buttonText = mc.font.plainSubstrByWidth(buttonText, widget.getWidth() - 6 - ellipsisWidth).trim() + "...";

        widget.drawCenteredString(poseStack,mc.font, buttonText, widget.x + widget.getWidth() / 2, widget.y + (widget.getHeight() - 8) / 2, color);
    }


    @Override
    public void renderSlider(PoseStack poseStack, IStyledSlider<?> slider, int mouseX, int mouseY) {
        GuiUtil.drawContinuousTexturedBox(poseStack,AbstractWidget.WIDGETS_LOCATION, (int) (slider.getWidget().x + (slider.getWidget().getWidth() - 8) * ((slider.getValue().floatValue() - slider.getMin().floatValue()) / Math.max(1, (slider.getMax().floatValue() - slider.getMin().floatValue())))), slider.getWidget().y, 0, 66, 8, slider.getWidget().getHeight(), 200, 20, 2, 3, 2, 2, slider.getOffsetBlit());
    }
}
