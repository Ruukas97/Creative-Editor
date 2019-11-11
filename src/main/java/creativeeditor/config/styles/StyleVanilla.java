package creativeeditor.config.styles;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;

public class StyleVanilla extends Style {
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
	public void renderButton(CEWButton button, int mouseX, int mouseY, float alpha) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		int j = getMainColor().getInt();

		minecraft.getTextureManager().bindTexture(Widget.WIDGETS_LOCATION);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
		int i = button.getYImage(button.isHovered());
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		button.blit(button.x, button.y, 0, 46 + i * 20, button.getWidth() / 2, button.getHeight());
		button.blit(button.x + button.getWidth() / 2, button.y, 200 - button.getWidth() / 2, 46 + i * 20, button.getWidth() / 2, button.getHeight());

		button.renderBg(minecraft, mouseX, mouseY);

		button.drawCenteredString(fontrenderer, button.getMessage(), button.x + button.getWidth() / 2, button.y + (button.getHeight() - 8) / 2,
				j | MathHelper.ceil(alpha * 255.0F) << 24);
	}
}
