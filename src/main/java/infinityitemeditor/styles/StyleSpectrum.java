package infinityitemeditor.styles;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.util.math.Mth;

public class StyleSpectrum implements Style {
    private int spectrumTick = 0;
    private static final Color color = new Color(0xFFFF0000);
    private static final Color inactive = new Color(0xFFDD00DD);
    private static final Color hovered = new Color(0xFFFF9900);


    @Override
    public Color getMainColor() {
        return color.copy();
    }


    @Override
    public void update() {
        spectrumTick++;
        spectrumTick %= 5;
        if (spectrumTick == 0) {
            color.hueShift();
            inactive.hueShift();
            hovered.hueShift();
        }
    }


    @Override
    public void renderBackground(PoseStack poseStack, ParentScreen screen) {
        GuiUtil.fillVerticalGradient(screen, 0, 0, screen.width, screen.height, -1072689136, -804253680);
    }


    @Override
    public void renderButton(PoseStack poseStack, IStyledWidget button, int mouseX, int mouseY, float alpha) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        int j = getFGColor(button.getWidget()).getInt();
        GuiUtil.drawFrame(poseStack,button.getWidget().x, button.getWidget().y, button.getWidget().x + button.getWidget().getWidth(), button.getWidget().y + button.getWidget().getHeight(), 1, new Color(j));
        button.renderBackground(poseStack,mc, mouseX, mouseY);

        String buttonText = button.getWidget().getMessage().getString();
        int strWidth = mc.font.width(buttonText);
        int ellipsisWidth = mc.font.width("...");

        if (strWidth > button.getWidget().getWidth() - 6 && strWidth > ellipsisWidth)
            buttonText = mc.font.plainSubstrByWidth(buttonText, button.getWidget().getWidth() - 6 - ellipsisWidth).trim() + "...";

        button.getWidget().drawCenteredString(poseStack,font, buttonText, button.getWidget().x + button.getWidget().getWidth() / 2, button.getWidget().y + (button.getWidget().getHeight() - 8) / 2, j | Mth.ceil(alpha * 255.0F) << 24);
        button.getWidget().drawCenteredString(poseStack,font, buttonText, button.getWidget().x + button.getWidget().getWidth() / 2, button.getWidget().y + (button.getWidget().getHeight() - 8) / 2, getMainColor().getInt());
    }


    @Override
    public void renderSlider(PoseStack poseStack, IStyledSlider<?> slider, int mouseX, int mouseY) {
        int x = slider.getWidget().x + 1 + (int) ((slider.getWidget().getWidth() - 3) * (slider.getValue().floatValue() - slider.getMin().floatValue()) / (slider.getMax().floatValue() - slider.getMin().floatValue()));
        AbstractGui.fill(poseStack,x, slider.getWidget().y + 3, x + 1, slider.getWidget().y + slider.getWidget().getHeight() - 3, getMainColor().getInt());
    }


    @Override
    public Color getFGColor(AbstractWidget widget) {
        return getFGColor(widget.active, widget.isHoveredOrFocused());
    }


    @Override
    public Color getFGColor(boolean active, boolean hovered) {
        if (!active)
            return inactive.copy();
        if (hovered)
            return StyleSpectrum.hovered.copy();
        return getMainColor();
    }
}
