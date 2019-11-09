package creativeeditor.events;

import creativeeditor.screen.CEScreenshotScreen;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CEScreenshotEvent {

	@SubscribeEvent
	public void onScreenShot(ScreenshotEvent e) {
		CEClientChatReceivedEvent.isEnabled = true;
		new CEScreenshotScreen(e);
		
	}
}
