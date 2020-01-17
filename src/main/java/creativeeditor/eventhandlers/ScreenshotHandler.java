package creativeeditor.eventhandlers;

import creativeeditor.screen.ScreenshotScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenshotHandler {

	
	@SubscribeEvent
	public void onScreenShot(ScreenshotEvent e) {
		Minecraft.getInstance().displayGuiScreen(new ScreenshotScreen(e));
		
	}
}
