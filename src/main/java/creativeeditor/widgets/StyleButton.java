package creativeeditor.widgets;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;

public class StyleButton extends Button{	
	public StyleButton(int x, int y, int width, int height, String text, IPressable onPress) {
		super(x, y, width, height, text, onPress);
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float unused) {
	      Minecraft minecraft = Minecraft.getInstance();
	      FontRenderer fontrenderer = minecraft.fontRenderer;
	      minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
	      GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
	      int i = this.getYImage(this.isHovered());
	      GlStateManager.enableBlend();
	      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	      this.blit(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
	      this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
	      this.renderBg(minecraft, mouseX, mouseY);
	      int j = getFGColor();

	      this.drawCenteredString(fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}
}
