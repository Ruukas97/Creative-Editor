package creativeeditor.util;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.screen.ParentScreen;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiUtils {
	public static boolean isMouseIn(int mouseX, int mouseY, int x, int y, int width, int height) {
		return x <= mouseX && mouseX < x + width && y <= mouseY && mouseY < y + height;
	}

	public static void drawFrame(int x, int y, int width, int height, int border, Color color) {
		AbstractGui.fill(x, y, width, y + border, color.getInt());
		AbstractGui.fill(x, y + border, x + border, height - border, color.getInt());
		AbstractGui.fill(width - border, y + border, width, height - border, color.getInt());
		AbstractGui.fill(x, height - border, width, height, color.getInt());
	}

	/**
	 * Public version of {@link AbstractGui#fillGradient}
	 */
	public static void fillGradient(ParentScreen screen, int xStart, int yStart, int xEnd, int yEnd, int fromColor,
			int toColor) {
		float f = (float) (fromColor >> 24 & 255) / 255.0F;
		float f1 = (float) (fromColor >> 16 & 255) / 255.0F;
		float f2 = (float) (fromColor >> 8 & 255) / 255.0F;
		float f3 = (float) (fromColor & 255) / 255.0F;
		float f4 = (float) (toColor >> 24 & 255) / 255.0F;
		float f5 = (float) (toColor >> 16 & 255) / 255.0F;
		float f6 = (float) (toColor >> 8 & 255) / 255.0F;
		float f7 = (float) (toColor & 255) / 255.0F;
		GlStateManager.disableTexture();
		GlStateManager.enableBlend();
		GlStateManager.disableAlphaTest();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos((double) xEnd, (double) yStart, (double) screen.getBlitOffset()).color(f1, f2, f3, f)
				.endVertex();
		bufferbuilder.pos((double) xStart, (double) yStart, (double) screen.getBlitOffset()).color(f1, f2, f3, f)
				.endVertex();
		bufferbuilder.pos((double) xStart, (double) yEnd, (double) screen.getBlitOffset()).color(f5, f6, f7, f4)
				.endVertex();
		bufferbuilder.pos((double) xEnd, (double) yEnd, (double) screen.getBlitOffset()).color(f5, f6, f7, f4)
				.endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlphaTest();
		GlStateManager.enableTexture();
	}
}
