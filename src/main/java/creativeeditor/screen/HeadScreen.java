package creativeeditor.screen;

import java.util.ArrayList;
import java.util.HashMap;

import creativeeditor.util.ColorUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadScreen extends ParentScreen {

	public enum HeadCategories {
		alphabet, animals, blocks, decoration, food_drinks, humans, humanoid, miscellaneous, monsters, plants
	}

	public HeadScreen(Screen lastScreen) {
		super(new TranslationTextComponent("gui.headcollection"), lastScreen);

	}

	public static HashMap<HeadCategories, ArrayList<ItemStack>> headLibrary = new HashMap<>();

	@Override
	protected void init() {
		super.init();

		minecraft.keyboardListener.enableRepeatEvents(true);

		addButton(new CEWButton(width / 24 - 12, height / 20 * 18, 60, 20, I18n.format("gui.main.back"),
				(Button b) -> {
					mc.displayGuiScreen(lastScreen);
				}));

		postInit();
	}

	protected void postInit() {

	}

	@Override
	public void removed() {
		minecraft.keyboardListener.enableRepeatEvents(false);

	}

	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		// Backspace not supported yet
		for (Widget w : renderWidgets) {
			if (w instanceof TextFieldWidget) {
				if (((TextFieldWidget) w).charTyped(p_charTyped_1_, p_charTyped_2_)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
		fill(width / 12, height / 10, width / 12 * 11, height / 10 * 9 - 3, new Color(100, 0, 0, 0).getInt());
		
		int i = 0;
		int add = height / 15;
		// Still requires fit for resizing
		for (HeadCategories hc : HeadCategories.values()) {
			drawCenteredString(mc.fontRenderer, I18n.format("gui.heads.category." + hc.name()), width / 40 * 7 + 5,
					height / 50 * 10 + i, color.getInt());
			i += add;
		}
		Color c = ColorUtils.multiplyColor(color, new Color(1, 220, 220, 220));

		// Main frame
		GuiUtils.drawFrame(width / 12, height / 10, width / 12 * 11, height / 10 * 9 - 3, 2, c);
		
		// Split line
		fill(width / 7 * 2 - 8, height / 10, width / 7 * 2 - 10, height / 10 * 8 + 7, c.getInt());

		// Top bar
		fill(width / 12, height / 10, width / 12 * 11, height / 13 * 2 + 2, c.getInt());
		
		// Top bar
		fill(width / 12, height / 10, width / 12 * 11, height / 13 * 2 + 2, c.getInt());

		// Bottom bar
		fill(width / 12, height / 10 * 8 + 6, width / 12 * 11, height / 10 * 8 + 8, c.getInt());

		// Credits to head api
		drawCenteredString(mc.fontRenderer, I18n.format("gui.heads.credit"), width / 13 * 4,
				height / 50 * 11 + i, color.getInt());

	}

}
