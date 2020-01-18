package creativeeditor.eventhandlers;

import creativeeditor.util.Screenshot;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenshotHandler {

	@SubscribeEvent
	public void onScreenShot(ScreenshotEvent e) {
		Screenshot ss = new Screenshot();
		ss.setHold(100);
		ss.makeScreenshot(e.getImage());

	}
}
