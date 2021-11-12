package infinityitemeditor.events;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NetworkHandler {

    @SubscribeEvent
    public void onNetwork(ClientPlayerNetworkEvent.LoggedInEvent e) {
        //System.out.println( e.getPlayer().world.getWorldInfo());
        // Get network version?
    }
}
