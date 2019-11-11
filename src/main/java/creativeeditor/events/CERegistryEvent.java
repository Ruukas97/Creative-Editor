package creativeeditor.events;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CERegistryEvent {

	@SubscribeEvent
	public void onRegister(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(
				//todo: add category to unavailable items
		);

	}
}
