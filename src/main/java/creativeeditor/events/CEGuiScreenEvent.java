package creativeeditor.events;

import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CEGuiScreenEvent {

	@SubscribeEvent()
	public void onScreen(GuiScreenEvent.InitGuiEvent.Pre e) {
		if (e.getGui() instanceof OptionsSoundsScreen) {
			e.getWidgetList().add(new Button(100, 100, 100, 100, "This is a test", (Button t) -> {
				
			}));
;			
		
		}
	}

}
