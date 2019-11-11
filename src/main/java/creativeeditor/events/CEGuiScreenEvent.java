package creativeeditor.events;

import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CEGuiScreenEvent {

	@SubscribeEvent()
	public void onScreen(GuiScreenEvent.InitGuiEvent e) {
		if (e.getGui() instanceof OptionsSoundsScreen) {
			int i = 11;
			e.addWidget(new Button(e.getGui().width / 2 - 155 + i % 2 * 160, e.getGui().height / 6 - 12 + 24 * (i >> 1),
					150, 20, I18n.format("gui.optionsound"), (Button t) -> {

					}));

		}
	}

}
