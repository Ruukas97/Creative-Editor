package creativeeditor.styles;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
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
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		int j = getMainColor().getInt();

		minecraft.getTextureManager().bindTexture(Widget.WIDGETS_LOCATION);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
		int i = button.getYImage(button.getWidget().isHovered());
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		button.getWidget().blit(button.getWidget().x, button.getWidget().y, 0, 46 + i * 20,
				button.getWidget().getWidth() / 2, button.getWidget().getHeight());
		button.getWidget().blit(button.getWidget().x + button.getWidget().getWidth() / 2, button.getWidget().y,
				200 - button.getWidget().getWidth() / 2, 46 + i * 20, button.getWidget().getWidth() / 2,
				button.getWidget().getHeight());

		button.renderBg(minecraft, mouseX, mouseY);

		button.getWidget().drawCenteredString(fontrenderer, button.getWidget().getMessage(),
				button.getWidget().x + button.getWidget().getWidth() / 2,
				button.getWidget().y + (button.getWidget().getHeight() - 8) / 2,
				j | MathHelper.ceil(alpha * 255.0F) << 24);
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
