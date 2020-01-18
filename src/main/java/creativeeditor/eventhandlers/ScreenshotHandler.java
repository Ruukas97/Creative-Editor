package creativeeditor.eventhandlers;

import creativeeditor.util.Screenshot;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenshotHandler {

	@SubscribeEvent
	public void onScreenShot(ScreenshotEvent e) {
		Screenshot ss = new Screenshot();
		ss.setHold(50);
		ss.makeScreenshot(e);
		e.setResultMessage(new StringTextComponent("Screenshot captured!"));
		e.setCanceled(true);

	}
}
