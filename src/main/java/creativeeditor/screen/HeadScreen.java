package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadScreen extends ParentScreen {

	enum HeadCategories {
		alphabet, animals, blocks, decoration, food_drinks, humans, humanoid, miscellaneous, monsters, plants
	}

	public HeadScreen(ITextComponent title, Screen lastScreen) {
		super(new TranslationTextComponent("gui.headcollection"), lastScreen);
	}

	@Override
	protected void init() {
		for (HeadCategories hc : HeadCategories.values()) {
			addButton(new CEWButton(50, 50, 50, 50, I18n.format("gui.heads.category." + hc.name()), (Button b) -> {

			}));
		}
		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2 + 60, 100, 20, "Heads", b -> {
			mc.displayGuiScreen(new HeadScreen(title, lastScreen));
		}));
		super.init();
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);

		// Main frame
		GuiUtils.drawFrame(width / 12, height / 10, width / 12 * 11, height / 10 * 9, 2, color);

		// Split line
		fill(width / 7 * 2 + 2, height / 10, width / 7 * 2, height / 10 * 9, color.getInt());

		// Top bar
		fill(width / 12, height / 10, width / 12 * 11, height / 13 * 2 + 5, color.getInt());

		Color shade = new Color(0, 0, 0, 120);

		// Search gradient WILL BE A TEXTBOX
		fill(width / 8 * 5, height / 10 + 2, width / 12 * 11 - 5, height / 13 * 2 + 5 - 2, shade.getInt());
		buttons.forEach(b -> {
			b.render(mouseX, mouseY, p3);
		});

	}

}
