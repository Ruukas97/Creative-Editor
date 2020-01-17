package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.client.event.ScreenshotEvent;

public class ScreenshotScreen extends Screen {

	private ScreenshotEvent e;
	@SuppressWarnings("unused")
	private boolean enableAnimation;
	private Minecraft mc;

	public ScreenshotScreen(ScreenshotEvent event) {
		super(null);
		if (event != null) {
			this.e = event;
			this.mc = Minecraft.getInstance();
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
	public void init(Minecraft p_init_1_, int p_init_2_, int p_init_3_) {
		// Calculate goal
	}

	@Override
	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		if (opacity > 7) {
			Color color = new Color(opacity, 255, 255, 255);
			fill(0, 0, width, height, color.getInt());
			if (hold <= 0) {
				opacity -= 7;
			} else {
				hold--;
			}
		}

	}

	public void displayScreen() {

		// needs delay but shouldnt be new thread
		DynamicTexture dyntex = new DynamicTexture(e.getImage());
		mc.textureManager.loadTexture(null, dyntex);
		dyntex.bindTexture();
		mc.ingameGUI.blit(59, 50, 50, 50, 20, 20);
		dyntex.close();

	}

	public void animationEnabled(boolean bool) {
		this.enableAnimation = bool;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
