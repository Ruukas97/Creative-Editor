package creativeeditor.screen;

import creativeeditor.config.styles.ColorStyles;
import creativeeditor.config.styles.ColorStyles.Style;
import creativeeditor.nbt.NBTItemBase;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;

public class MainScreen extends ParentItemScreen {

	public MainScreen(Screen lastScreen, NBTItemBase editing) {
		super(new StringTextComponent(I18n.format("gui.creativeeditor")), lastScreen, editing);
	}

	@Override
	protected void init() {
		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2, 100, 20, I18n.format("gui.main.itemflag"), t -> {
			minecraft.displayGuiScreen(new FlagScreen(title, lastScreen, editing));
		}));

		addButton(new CEWButton(width / 9 * 7 - 20, height / 11 * 2 + 30, 100, 20, I18n.format("gui.main.colorstyle"),
				b -> {
					ColorStyles.setStyle(ColorStyles.getStyle() == Style.vanilla ? Style.spectrum : Style.vanilla);
				}));

		super.init();
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);

		// First vertical line
		fill(width / 3, 20, width / 3 + 1, height - 20, color.getColor());
		// Second vertical line
		fill(width * 2 / 3, 20, width * 2 / 3 + 1, height - 20, color.getColor());
		// Left horizontal line
		fill(20, 40, width / 3 - 15, 41, color.getColor());
		// Right horizontal line
		fill(width * 2 / 3 + 16, 40, width - 20, 41, color.getColor());
	}
}
