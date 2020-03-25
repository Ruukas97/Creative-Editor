package creativeeditor.eventhandlers;

import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenshotHandler {

	@SubscribeEvent
	public void onScreenShot(ScreenshotEvent e) {
		/**Screenshot ss = new Screenshot(e);
		ss.setHold(50);
		e.setResultMessage(new StringTextComponent("Screenshot captured!"));
		e.setCanceled(true);
		**/

	}
}
