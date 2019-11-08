package creativeeditor.widgets;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.config.styles.ColorStyles;
import creativeeditor.config.styles.ColorStyles.Style;
import creativeeditor.util.ColorUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;

public class StyleButton extends Button {
	public StyleButton(int x, int y, int width, int height, String text, IPressable onPress) {
		super(x, y, width, height, text, onPress);
	}

	@Override
	public void renderButton(int mouseX, int mouseY, float unused) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		int j = getFGColor();

		if(ColorStyles.getStyle() == Style.vanilla) {
			minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
			int i = getYImage(isHovered());
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
			GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
			GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			blit(x, y, 0, 46 + i * 20, width / 2, height);
			blit(x + width / 2, y, 200 - width / 2, 46 + i * 20, width / 2, height);
		}
		else {
			GuiUtils.drawFrame(x, y, x+width, y+height, 1, new Color( j ));
		}
		this.renderBg(minecraft, mouseX, mouseY);

		this.drawCenteredString(fontrenderer, getMessage(), x + width / 2, y + (height - 8) / 2, j | MathHelper.ceil(alpha * 255.0F) << 24);
	}

	@Override
	public int getFGColor() {
		if (ColorStyles.getStyle() == Style.spectrum) {
			if (!active)
				return new ColorUtils.Color(255, 120, 120, 120).getColor();
			else if (isHovered())
				return ColorUtils.multiplyColor(ColorStyles.getMainColor().getColor(), new ColorUtils.Color(255, 170, 170, 170).getColor());
			else
				return ColorStyles.getMainColor().getColor();
		} else {
			if (!active)
				return 10526880;
			else if (isHovered())
				return 16777120;
			else
				return 14737632;
		}

	}
}
