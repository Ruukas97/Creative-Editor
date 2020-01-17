package creativeeditor.screen;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class ScreenshotScreen extends Screen {

	private boolean enableAnimation;
	private DynamicTexture dyntex;
	private ResourceLocation screenshotLocation = new ResourceLocation("creativeeditor", "screenshot");

	public ScreenshotScreen(@Nonnull ScreenshotEvent event) {
		super(new TranslationTextComponent("gui.screenshot"));
		if (event != null) {
			dyntex = new DynamicTexture(event.getImage());
		} else {
			onClose();
		}
	}

	int opacity = 252;
	int hold = 20;
	double speed = 0.95;
	int x;
	int x2;
	int y;
	int y2;

	@Override
	public void init(Minecraft mc, int width, int height) {
		//int i = mc.mainWindow.calcGuiScale(1, false);
		//mc.mainWindow.setGuiScale(i);
		super.init(mc, mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight());
		minecraft.textureManager.loadTexture(screenshotLocation, dyntex);
	}

	@Override
	public void onClose() {
		super.onClose();
		dyntex.close();
		minecraft.textureManager.deleteTexture(screenshotLocation);
		minecraft.updateWindowSize();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);
		if (opacity > 7) {
			Color color = new Color(opacity, 255, 255, 255);
			fill(0, 0, width, height, color.getInt());
			if (hold <= 0) {
				opacity -= 7;
			} else {
				hold--;
			}
		} else {
			displayScreen();
		}
	}

	public void displayScreen() {
        minecraft.getTextureManager().bindTexture(screenshotLocation);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiUtils.drawInscribedRect(50, 50, width-100, height-100, dyntex.getTextureData().getWidth(), dyntex.getTextureData().getHeight());
	}

	public void animationEnabled(boolean bool) {
		this.enableAnimation = bool;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
