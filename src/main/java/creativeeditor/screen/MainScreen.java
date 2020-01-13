package creativeeditor.screen;

import java.util.Arrays;
import java.util.List;

import creativeeditor.config.ConfigHandler;
import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRange;
import creativeeditor.data.base.DataString;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.SliderTag;
import creativeeditor.widgets.StringTag;
import creativeeditor.widgets.StyledTextButton;
import creativeeditor.widgets.StyledToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class MainScreen extends ParentItemScreen {

	private StyledTextButton nbtButton, tooltipButton, toolsButton, loreButton, editButton, advancedButton;

	// Tools
	private StyledTextButton styleButton;

	// Lore
	private StringTag nameField;

	public MainScreen(Screen lastScreen, DataItem editing) {
		super(new TranslationTextComponent("gui.main"), lastScreen, editing);
	}

	public void tick() {
		nameField.tick();
	}

	@Override
	protected void init() {
		super.init();

		minecraft.keyboardListener.enableRepeatEvents(true);

		String nbtLocal = I18n.format("gui.main.nbt");
		String tooltipLocal = I18n.format("gui.main.tooltip");
		String toolsLocal = I18n.format("gui.main.tools");
		String loreLocal = I18n.format("gui.main.display");
		String editLocal = I18n.format("gui.main.data");
		String advancedLocal = I18n.format("gui.main.other");

		int nbtWidth = font.getStringWidth(nbtLocal);
		int tooltipWidth = font.getStringWidth(tooltipLocal);
		int toolsWidth = font.getStringWidth(toolsLocal);
		int loreWidth = font.getStringWidth(loreLocal);
		int editWidth = font.getStringWidth(editLocal);
		int advancedWidth = font.getStringWidth(advancedLocal);

		int nbtX = 21 + nbtWidth / 2;
		int toolsX = width / 3 - 16 - toolsWidth / 2;
		int tooltipX = (nbtX + toolsX) / 2;

		int advancedX = width - 21 - advancedWidth / 2;
		int loreX = 2 * width / 3 + 17 + loreWidth / 2;
		int editX = ((loreX + loreWidth / 2) + (advancedX - advancedWidth / 2)) / 2;

		nbtButton = addButton(new StyledTextButton(nbtX, 35, nbtWidth, nbtLocal, b -> {
			ConfigHandler.CLIENT.currentLeftSideview.set(0);
			nbtButton.active = false;
			tooltipButton.active = true;
			toolsButton.active = true;
			styleButton.visible = false;
		}));

		tooltipButton = addButton(new StyledTextButton(tooltipX, 35, tooltipWidth, tooltipLocal, b -> {
			ConfigHandler.CLIENT.currentLeftSideview.set(1);
			nbtButton.active = true;
			tooltipButton.active = false;
			toolsButton.active = true;
			styleButton.visible = false;
		}));

		toolsButton = addButton(new StyledTextButton(toolsX, 35, toolsWidth, toolsLocal, b -> {
			ConfigHandler.CLIENT.currentLeftSideview.set(2);
			nbtButton.active = true;
			tooltipButton.active = true;
			toolsButton.active = false;
			styleButton.visible = true;
		}));

		loreButton = addButton(new StyledTextButton(loreX, 35, loreWidth, loreLocal, b -> {
			ConfigHandler.CLIENT.currentRightSideview.set(0);
			loreButton.active = false;
			editButton.active = true;
			advancedButton.active = true;
			nameField.visible = true;
		}));

		editButton = addButton(new StyledTextButton(editX, 35, editWidth, editLocal, b -> {
			ConfigHandler.CLIENT.currentRightSideview.set(1);
			loreButton.active = true;
			editButton.active = false;
			advancedButton.active = true;
			nameField.visible = false;
		}));

		advancedButton = addButton(new StyledTextButton(advancedX, 35, advancedWidth, advancedLocal, b -> {
			ConfigHandler.CLIENT.currentRightSideview.set(2);
			loreButton.active = true;
			editButton.active = true;
			advancedButton.active = false;
			nameField.visible = false;
		}));

		// Tools
		String styleLocal = I18n.format("gui.main.style");
		styleButton = addButton(new StyledTextButton((15 + width / 3) / 2, 55, font.getStringWidth(styleLocal),
				styleLocal, b -> StyleManager.setNext()));

		// Lore
		nameField = new StringTag(font, width - width / 6 - 30, 55, 60, 20,
				item.getItemNBTTag().getDisplayTag().getNameTag(item));
		children.add(nameField);
		System.out.println(((DataString) nameField.getDataTag()).get());

		// General Item
		addButton(new SliderTag(width / 2 + 5, 61, 50, 16, item.getCountTag()));

		addButton(new SliderTag(width / 2 + 5, 81, 50, 16, item.getItemNBTTag().getDamageTag(item)));

		addButton(new StyledToggle(width / 2 - 40, 101, 80, 16, "item.tag.unbreakable.true",
				"item.tag.unbreakable.false", item.getItemNBTTag().getUnbreakableTag()));

		switch (ConfigHandler.CLIENT.currentLeftSideview.get()) {
		case 0:
			nbtButton.active = false;
			styleButton.visible = false;
			break;
		case 1:
			tooltipButton.active = false;
			styleButton.visible = false;
			break;
		case 2:
			toolsButton.active = false;
			styleButton.visible = true;
		}

		switch (ConfigHandler.CLIENT.currentRightSideview.get()) {
		case 0:
			loreButton.active = false;
			nameField.visible = true;
			break;
		case 1:
			editButton.active = false;
			nameField.visible = false;
			break;
		case 2:
			advancedButton.active = false;
			nameField.visible = false;
		}
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
		return super.keyPressed(key1, key2, key3);
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
	public void mainRender(int mouseX, int mouseY, float partialTicks, Color color) {
		super.mainRender(mouseX, mouseY, partialTicks, color);

		// Item Name
		String itemCount = item.getCount() > 1 ? item.getCount() + "x " : "";
		String itemOverview = itemCount + item.getItemStack().getDisplayName().getFormattedText();
		drawCenteredString(font, itemOverview, width / 2, 27, color.getInt());

		String id = I18n.format("gui.main.id");
		int idWidth = font.getStringWidth(id);
		drawString(font, id, width / 2 - idWidth, 45, color.getInt());
		drawString(font, item.getItem().getRegistryName().getPath(), width / 2 + 5, 45, color.getInt());

		String count = I18n.format("gui.main.count");
		int countWidth = font.getStringWidth(count);
		drawString(font, count, width / 2 - countWidth, 65, color.getInt());

		String damage = I18n.format("gui.main.damage");
		int damageWidth = font.getStringWidth(damage);
		drawString(font, damage, width / 2 - damageWidth, 85, color.getInt());

		nameField.render(mouseY, mouseY, partialTicks);
	}

	@Override
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		super.overlayRender(mouseX, mouseY, p3, color);

		if (ConfigHandler.CLIENT.currentLeftSideview.get() == 0) {
			// NBT
			List<String> nbtLines = Arrays
					.asList(item.getItemNBT().toFormattedComponent(" ", 0).getFormattedText().split("\n"));

			renderTooltip(nbtLines, 0, 60);
		}

		else if (ConfigHandler.CLIENT.currentLeftSideview.get() == 1) {
			renderTooltip(item.getItemStack(), 0, 60);
		}
	}
}
