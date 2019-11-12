package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.CEWButton;
import creativeeditor.widgets.StyleTextField;
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
		int i = 0;
		// Still requires fit for resizing
		for (HeadCategories hc : HeadCategories.values()) {
			addButton(new CEWButton(45, 54 + i, 78, 20, I18n.format("gui.heads.category." + hc.name()), (Button b) -> {

			}));
			i+= 21;
		}
		addButton(new CEWButton(width / 24 - 12, height / 13 * 12 - 4, 60, 20, I18n.format("gui.main.back"), (Button b) -> {
			mc.displayGuiScreen(lastScreen);
		}));
		
		renderWidgets.add(new StyleTextField(width / 8 * 5, height / 10 + 2, width / 12 * 3, 14 , ""));
		super.init();
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);

		// Main frame
		GuiUtils.drawFrame(width / 12, height / 10, width / 12 * 11, height / 10 * 9 - 3, 2, color);

		// Split line
		fill(width / 7 * 2 - 8, height / 10, width / 7 * 2 - 10, height / 10 * 9 - 3, color.getInt());

		// Top bar
		fill(width / 12, height / 10, width / 12 * 11, height / 13 * 2 + 5, color.getInt());
		
		

	}

}
