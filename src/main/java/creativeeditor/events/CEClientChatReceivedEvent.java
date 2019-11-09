package creativeeditor.events;

import creativeeditor.screen.CEScreenshotScreen;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CEClientChatReceivedEvent {

	public static boolean isEnabled = false;

	@SubscribeEvent
	public void onReceive(ClientChatReceivedEvent e) { //event didn't run, needs fix
		System.out.println("te2t");
		if (isEnabled) {
			System.out.println("test");
			
			//keep in mind of translation
			if (e.getMessage().toString().startsWith("Saved screenshot as")) {
				System.out.println("PRINTS");
				CEScreenshotScreen ecs = new CEScreenshotScreen(null);
				ecs.displayScreen();
				isEnabled = false;
				
			}
		}
	}
}
