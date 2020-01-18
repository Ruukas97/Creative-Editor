package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.eventhandlers.GameOverlayHandler;
import creativeeditor.util.Screenshot;
import creativeeditor.widgets.StyledTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class ScreenshotScreen extends Screen {

	private DynamicTexture dyntex;
	private ResourceLocation screenshotLocation;
	private StyledTextField titleField;
	private ScreenshotEvent e;

	public ScreenshotScreen() {
		super(new TranslationTextComponent("gui.screenshot"));
		this.screenshotLocation = Screenshot.screenshotLocation;
		this.e = GameOverlayHandler.event;
	}

	@Override
	public void init(Minecraft mc, int width, int height) {
		titleField = new StyledTextField(mc.fontRenderer, 100, 10, 250, 18, e.getScreenshotFile().getName());
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
		titleField.render(mouseX, mouseY, partialTicks);

	}

	public void displayScreen() {
		minecraft.getTextureManager().bindTexture(screenshotLocation);
		GlStateManager.enableBlend();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiUtils.drawInscribedRect(10, -10, 100, 100, dyntex.getTextureData().getWidth(),
				dyntex.getTextureData().getHeight());
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		titleField.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);

	}

	@Override
	public void tick() {
		titleField.tick();
		super.tick();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
