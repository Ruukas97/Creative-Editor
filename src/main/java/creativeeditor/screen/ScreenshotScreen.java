package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.util.Screenshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class ScreenshotScreen extends Screen {

	private DynamicTexture dyntex;
	private ResourceLocation screenshotLocation;
	
	public ScreenshotScreen(DynamicTexture dyntex) {
		super(new TranslationTextComponent("gui.screenshot"));
		this.dyntex = dyntex;
		this.screenshotLocation = Screenshot.screenshotLocation;
	}

	@Override
	public void init(Minecraft mc, int width, int height) {
		// int i = mc.mainWindow.calcGuiScale(1, false);
		// mc.mainWindow.setGuiScale(i);
		super.init(mc, mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight());
	}

	@Override
	public void onClose() {
		super.onClose();
		minecraft.updateWindowSize();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);

	}

	public void displayScreen() {
		minecraft.getTextureManager().bindTexture(screenshotLocation);
		GlStateManager.enableBlend();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiUtils.drawInscribedRect(10, -10, 100, 100, dyntex.getTextureData().getWidth(),
				dyntex.getTextureData().getHeight());
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
