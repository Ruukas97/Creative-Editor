package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.styles.StyleManager;
import creativeeditor.styles.StyleSpectrum;
import creativeeditor.styles.StyleVanilla;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.SliderTag;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class MainScreen extends ParentItemScreen {

	public MainScreen(Screen lastScreen, DataItem editing) {
		super(new TranslationTextComponent("gui.main"), lastScreen, editing);
	}

	public void tick() {
	}

	@Override
	protected void init() {
		super.init();

		minecraft.keyboardListener.enableRepeatEvents(true);

		// Might want to put the buttons in a list and loop them
		addButton(
				new StyledButton(width / 9 * 7 - 20, height / 11 * 2, 100, 20, I18n.format("gui.main.itemflag"), b -> {
					minecraft.displayGuiScreen(new FlagScreen(this, item));
				}));

		addButton(new StyledButton(width / 9 * 7 - 20, height / 11 * 2 + 30, 100, 20,
				I18n.format("gui.main.colorstyle"), b -> {
					StyleManager.setCurrentStyle(
							StyleManager.getCurrentStyle() instanceof StyleVanilla ? new StyleSpectrum()
									: new StyleVanilla());
				}));

		addButton(
				new StyledButton(width / 9 * 7 - 20, height / 11 * 2 + 60, 100, 20, I18n.format("gui.main.head"), b -> {
					mc.displayGuiScreen(new HeadScreen(this));
				}));

		addButton(new StyledButton(width / 9 * 7 - 20, height / 11 * 2 + 90, 100, 20, I18n.format("gui.main.player"),
				b -> {
					mc.displayGuiScreen(new PlayerScreen(this));
				}));

		addButton(new SliderTag(width / 2 + 5, 60, 50, 20, item.getCountTag()));
	}

	@Override
	public void resize(Minecraft minecraft, int par2, int par3) {
		this.init(minecraft, par2, par3);
	}

	@Override
	public void removed() {
		this.minecraft.keyboardListener.enableRepeatEvents(false);
	}

	@Override
	public boolean keyPressed(int key1, int key2, int key3) {
		// TODO Esc close or unselect
		// TODO Tab select next element
		return false;
	}

	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		super.backRender(mouseX, mouseY, p3, color);

		// First vertical line
		fill(width / 3, 20, width / 3 + 1, height - 20, color.getInt());
		// Second vertical line
		fill(width * 2 / 3, 20, width * 2 / 3 + 1, height - 20, color.getInt());
		// Left horizontal line
		fill(20, 40, width / 3 - 15, 41, color.getInt());
		// Right horizontal line
		fill(width * 2 / 3 + 16, 40, width - 20, 41, color.getInt());
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);

		// Item Name
		String itemCount = item.getCount() > 1 ? item.getCount() + "x " : "";
		String itemOverview = itemCount + item.getItemStack().getDisplayName().getFormattedText();
		drawCenteredString(font, itemOverview, width / 2, 27, color.getInt());

		String id = I18n.format("gui.main.id");
		int idWidth = font.getStringWidth(id);
		drawString(font, id, width / 2 - idWidth, 47, color.getInt());
		drawString(font, item.getItem().getRegistryName().getPath(), width / 2 + 5, 47, color.getInt());

		String count = I18n.format("gui.main.count");
		int countWidth = font.getStringWidth(count);
		drawString(font, count, width / 2 - countWidth, 67, color.getInt());
		// drawString(font, ""+editing.getCount(), width / 2 + 5, 67, color.getInt());

	}
}
