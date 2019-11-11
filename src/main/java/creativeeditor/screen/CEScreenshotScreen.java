package creativeeditor.screen;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraftforge.client.event.ScreenshotEvent;

public class CEScreenshotScreen {

	private ScreenshotEvent e;
	private boolean enableAnimation;
	private Minecraft mc;

	public CEScreenshotScreen(ScreenshotEvent event) {
		if (event != null) {
			this.e = event;
			this.mc = Minecraft.getInstance();
		}

	}

	public void displayScreen() {
		mc.execute(() -> {
			// needs delay but shouldnt be new thread
			File file = e.getScreenshotFile();
			try {
				InputStream targetStream;
				targetStream = new FileInputStream(file);
				NativeImage ni = NativeImage.read(targetStream);
				DynamicTexture dyntex = new DynamicTexture(ni);
				mc.textureManager.loadTexture(null, dyntex);
				dyntex.bindTexture();

				mc.ingameGUI.blit(59, 50, 50, 50, 20, 20);
				dyntex.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		

	}

	public void shouldPlayAnimation(boolean bool) {
		this.enableAnimation = bool;
	}

}
