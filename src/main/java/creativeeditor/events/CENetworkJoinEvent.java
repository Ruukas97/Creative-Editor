package creativeeditor.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CENetworkJoinEvent {

	
	@SubscribeEvent
	public void onNetwork(ClientPlayerNetworkEvent.LoggedInEvent e) {
		System.out.println(Minecraft.getInstance().getIntegratedServer().getMinecraftVersion());
		// Get network version?
	}
}
