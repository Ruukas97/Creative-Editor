package creativeeditor.styles;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.fml.client.config.GuiUtils;

public class StyleVanilla extends StyleBase {
	public StyleVanilla() {
		super(new StaticButtonColor(16777120, 14737632, 10526880));
	}

	private Color mainColor = new Color(255, 200, 200, 200);

	@Override
	public Color getMainColor() {
		return mainColor;
	}

	@Override
	public void renderBackground(ParentScreen screen) {
		screen.renderBackground();
	}

	@Override
	public void renderButton(IStyledWidget button, int mouseX, int mouseY, float alpha) {
		Widget widget = button.getWidget();
		if (!widget.visible)
			return;

		Minecraft mc = Minecraft.getInstance();
		button.setHovered(mouseX >= widget.x && mouseY >= widget.y && mouseX < widget.x + widget.getWidth()
				&& mouseY < widget.y + widget.getHeight());
		int k = button.getYImage(widget.isHovered());
		GuiUtils.drawContinuousTexturedBox(Widget.WIDGETS_LOCATION, widget.x, widget.y, 0, 46 + k * 20,
				widget.getWidth(), widget.getHeight(), 200, 20, 2, 3, 2, 2, button.getBlitOffset());
		button.renderBg(mc, mouseX, mouseY);

		int color = getFGColor(widget).getInt();

		String buttonText = widget.getMessage();
		int strWidth = mc.fontRenderer.getStringWidth(buttonText);
		int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

		if (strWidth > widget.getWidth() - 6 && strWidth > ellipsisWidth)
			buttonText = mc.fontRenderer.trimStringToWidth(buttonText, widget.getWidth() - 6 - ellipsisWidth).trim()
					+ "...";

		widget.drawCenteredString(mc.fontRenderer, buttonText, widget.x + widget.getWidth() / 2,
				widget.y + (widget.getHeight() - 8) / 2, color);
	}

	@Override
	public void renderSlider(IStyledSlider slider, int mouseX, int mouseY) {
		GuiUtils.drawContinuousTexturedBox(Widget.WIDGETS_LOCATION,
				(int) (slider.getWidget().x
						+ (slider.getWidget().getWidth() - 8) * ((float) (slider.getValue() - slider.getMin())
								/ (float) (slider.getMax() - slider.getMin()))),
				slider.getWidget().y, 0, 66, 8, slider.getWidget().getHeight(), 200, 20, 2, 3, 2, 2,
				slider.getBlitOffset());
	}
}
