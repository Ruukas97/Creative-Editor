package creativeeditor.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;

public class StyleSpectrum implements Style {
	private int spectrumTick = 0;

	@Override
	public Color getMainColor() {
		return ColorUtils.hsvToRGB(spectrumTick / 5000f, 1f, 1f);
	}

	@Override
	public void update() {
		spectrumTick = (spectrumTick + 1) % 5000;
	}

	@Override
	public void renderBackground(ParentScreen screen) {
		GuiUtils.fillGradient(screen, 0, 0, screen.width, screen.height, -1072689136, -804253680);
	}

	@Override
	public void renderButton(IStyledWidget button, int mouseX, int mouseY, float alpha) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.fontRenderer;
		int j = getFGColor(button.getWidget()).getInt();
		GuiUtils.drawFrame(button.getWidget().x, button.getWidget().y,
				button.getWidget().x + button.getWidget().getWidth(),
				button.getWidget().y + button.getWidget().getHeight(), 1, new Color(j));
		button.renderBg(mc, mouseX, mouseY);
		button.getWidget().drawCenteredString(font, button.getWidget().getMessage(),
				button.getWidget().x + button.getWidget().getWidth() / 2,
				button.getWidget().y + (button.getWidget().getHeight() - 8) / 2,
				j | MathHelper.ceil(alpha * 255.0F) << 24);
		
        String buttonText = button.getWidget().getMessage();
        int strWidth = mc.fontRenderer.getStringWidth(buttonText);
        int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

        if (strWidth > button.getWidget().getWidth() - 6 && strWidth > ellipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, button.getWidget().getWidth() - 6 - ellipsisWidth).trim() + "...";

        button.getWidget().drawCenteredString(mc.fontRenderer, buttonText, button.getWidget().x + button.getWidget().getWidth() / 2, button.getWidget().y + (button.getWidget().getHeight() - 8) / 2, getMainColor().getInt());
	}

	@Override
	public void renderSlider(IStyledSlider slider, int mouseX, int mouseY) {
		int x = slider.getWidget().x + 1 + (int) ((slider.getWidget().getWidth() - 3)
				* (slider.getValue() - slider.getMin()) / (float) (slider.getMax() - slider.getMin()));
		AbstractGui.fill(x, slider.getWidget().y + 3, x + 1, slider.getWidget().y + slider.getWidget().getHeight() - 3,
				getMainColor().getInt());
	}

	@Override
	public Color getFGColor(Widget widget) {
		if (!widget.active)
			return ColorUtils.hsvToRGB(((spectrumTick - 350) % 5000) / 5000f, 0.85f, 1f);
		if (widget.isHovered())
			return ColorUtils.hsvToRGB(((spectrumTick + 500) % 5000) / 5000f, 1f, 1f);
		return getMainColor();
	}
}
