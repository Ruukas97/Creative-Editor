package creativeeditor.events;

import creativeeditor.screen.CEOptionSoundScreen;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CEGuiScreenEvent {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onScreen(GuiScreenEvent.InitGuiEvent.Pre e) {
		if (e.getGui() instanceof OptionsSoundsScreen) {
			e.setCanceled(true);
			GameSettings gs = Minecraft.getInstance().gameSettings;
			Minecraft.getInstance().displayGuiScreen(new CEOptionSoundScreen(new OptionsScreen(null, gs), gs));
			//TO DO: FIX 2 TIMES BUTTON CLICK SOUND BUG
		
		}
	}

}
