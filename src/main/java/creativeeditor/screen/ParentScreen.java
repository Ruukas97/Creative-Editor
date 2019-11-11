package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.config.styles.ColorStyles;
import creativeeditor.config.styles.ColorStyles.Style;
import creativeeditor.nbt.NBTItemBase;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.ITextComponent;

public class ParentScreen extends Screen {
	protected final Screen lastScreen;
	protected NBTItemBase editing;

	// Back, reset, drop, save button (has essential buttons)
	protected boolean hasEssButtons = false;

	// render item
	protected boolean renderItem = true;
	protected float itemScale = 2.0f;

	// render tooltip top left
	boolean renderToolTip = false;

	protected Minecraft mc;

	public ParentScreen(ITextComponent title, Screen lastScreen, NBTItemBase editing) {
		super(title);
		this.lastScreen = lastScreen;
		this.editing = editing;
		this.mc = Minecraft.getInstance();
	}

	@Override
	protected void init() {

		// Render essential buttons NOT WORKING ATM
		if (hasEssButtons) {
			int bwidth = 50;
			int posX = width / 2 - (bwidth / 2);
			int posY = height / 7 * 6;
			addButton(new CEWButton(posX - 49, posY, bwidth, 20, "Back", (Button b) -> {
				mc.displayGuiScreen(lastScreen);
			}));
			addButton(new CEWButton(posX, posY - 9, bwidth, 20, "Drop", (Button b) -> {
				mc.player.inventory.player.dropItem(editing.getItemStack(), true);
				mc.playerController.sendPacketDropItem(editing.getItemStack());
				// Shift for /give
			}));
			addButton(new CEWButton(posX, posY + 9, bwidth, 20, "Close", (Button b) -> {
				this.onClose();
			}));
			addButton(new CEWButton(posX + 49, posY, bwidth, 20, "Apply", (Button b) -> {
				// Hold shift to copy to realm?
			}));
		}
		super.init();
	}

	@Override
	public void resize(Minecraft mc, int width, int height) {
		super.resize(mc, width, height);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public boolean keyPressed(int key1, int key2, int key3) {
		return super.keyPressed(key1, key2, key3);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public void setRenderItem(boolean shouldRender, float scale) {
		this.renderItem = shouldRender;
		if (scale > 0)
			this.itemScale = scale;
	}

	@Override
	public void render(int mouseX, int mouseY, float p3) {
		Color color = ColorStyles.getMainColor();
		backRender(mouseX, mouseY, p3, color);
		mainRender(mouseX, mouseY, p3, color);
		overlayRender(mouseX, mouseY, p3, color);
	}

	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		if (ColorStyles.getStyle() == Style.vanilla)
			renderBackground();
		else
			fillGradient(0, 0, width, height, -1072689136, -804253680);

		// Frame
		GuiUtils.drawFrame(5, 5, width - 5, height - 5, 1, color);

		// GUI Title
		drawCenteredString(font, getTitle().getFormattedText(), width / 2, 9, color.getColor());
		int sWidthHalf = font.getStringWidth(getTitle().getFormattedText()) / 2 + 3;
		
		// Title underline
		AbstractGui.fill(width / 2 - sWidthHalf, 20, width / 2 + sWidthHalf, 21, color.getColor());

	}

	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		// Item Name
		String itemName = editing.getItemStack().getDisplayName().getFormattedText();
		drawCenteredString(font, itemName, width / 2, 27,
				color.getColor());

		buttons.forEach(b -> {
			b.render(mouseX, mouseY, p3);
		});
	}

	/**
	 * Should always be called last in render, but only once.
	 */
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		// Item (Tooltip must render last or colors will be messed up)
		if (renderItem) {
			GlStateManager.scalef(itemScale, itemScale, 1f);
			RenderHelper.enableGUIStandardItemLighting();
			itemRenderer.renderItemIntoGUI(editing.getItemStack(), (int) (width / (2 * itemScale) - 8),
					(int) (height / (2 * itemScale + 2) - 15));

			GlStateManager.scalef(1f / itemScale, 1f / itemScale, 1f);
			// Item frame
			GuiUtils.drawFrame(width / 2 - 18, height / 3 - 32, width / 2 + 18, height / 3 + 5, 1, color); // Add fill
																											// to
																											// "drawframe"
			// Item tooltip STILL NEEDS IMPROVEMENT
			if (GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 16, height / 3 - 30, 32, 32)) {
				renderTooltip(editing.getItemStack(), mouseX, mouseY);
			}
		}

		ColorStyles.tickSpectrum();
	}
}
