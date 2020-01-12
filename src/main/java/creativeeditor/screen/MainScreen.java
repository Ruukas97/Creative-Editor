package creativeeditor.screen;

import java.util.Arrays;
import java.util.List;

import creativeeditor.config.ConfigHandler;
import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRange;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.SliderTag;
import creativeeditor.widgets.StyledTextButton;
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

		String nbtLocal = I18n.format("gui.main.nbt");
		String tooltipLocal = I18n.format("gui.main.tooltip");
		String toolsLocal = I18n.format("gui.main.tools");
		String rightOne = I18n.format("gui.main.display");
		String rightTwo = I18n.format("gui.main.data");
		String rightThree = I18n.format("gui.main.other");

		int nbtWidth = font.getStringWidth(nbtLocal);
		int tooltipWidth = font.getStringWidth(tooltipLocal);
		int toolsWidth = font.getStringWidth(toolsLocal);
		int rightOneWidth = font.getStringWidth(rightOne);
		int rightTwoWidth = font.getStringWidth(rightTwo);
		int rightThreeWidth = font.getStringWidth(rightThree);

		int nbtX = 21 + nbtWidth / 2;
		int toolsX = width / 3 - 16 - toolsWidth / 2;
		int tooltipX = (nbtX + toolsX) / 2;

		int rightThreeX = width - 21 - rightThreeWidth / 2;
		int rightOneX = 2 * width / 3 + 17 + rightOneWidth / 2;
		int rightTwoX = ((rightOneX + rightOneWidth / 2) + (rightThreeX - rightThreeWidth / 2)) / 2;

		addButton(new StyledTextButton(nbtX, 35, nbtWidth, nbtLocal, b -> {
			ConfigHandler.CLIENT.currentLeftSideview.set(0);
		}));

		addButton(new StyledTextButton(tooltipX, 35, tooltipWidth, tooltipLocal, b -> {
			ConfigHandler.CLIENT.currentLeftSideview.set(1);
		}));

		addButton(new StyledTextButton(toolsX, 35, toolsWidth, toolsLocal, b -> {
			ConfigHandler.CLIENT.currentLeftSideview.set(2);
		}));

		addButton(new StyledTextButton(rightOneX, 35, rightOneWidth, rightOne, b -> {
			ConfigHandler.CLIENT.currentRightSideview.set(0);
		}));

		addButton(new StyledTextButton(rightTwoX, 35, rightTwoWidth, rightTwo, b -> {
			ConfigHandler.CLIENT.currentRightSideview.set(1);
		}));

		addButton(new StyledTextButton(rightThreeX, 35, rightThreeWidth, rightThree, b -> {
			ConfigHandler.CLIENT.currentRightSideview.set(2);
		}));


		addButton(new SliderTag(width / 2 + 5, 61, 50, 16, item.getCountTag()));

		addButton(new SliderTag(width / 2 + 5, 81, 50, 16,
				new NumberRange(item.getItemNBTTag().getDamageTag(), 0, item.getItemStack().getMaxDamage())));
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
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);

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
