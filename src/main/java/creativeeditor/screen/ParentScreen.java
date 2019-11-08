package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.config.styles.ColorStyles;
import creativeeditor.config.styles.ColorStyles.Style;
import creativeeditor.nbt.NBTItemBase;
import creativeeditor.util.GuiUtils;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.ITextComponent;

public class ParentScreen extends Screen {
	protected final Screen lastScreen;
	protected NBTItemBase editing;

	// Back, reset, drop, save button
	protected boolean hasSave = false;
	
	// Helps for resetting gui scale back to normal on closing
	private boolean closing = false;

	// render item
	protected boolean renderItem = true;
	protected float itemScale = 2.0f;

	// render tooltip top left
	boolean renderToolTip = false;

	public ParentScreen(ITextComponent title, Screen lastScreen, NBTItemBase editing) {
		super(title);
		this.lastScreen = lastScreen;
		this.editing = editing;
	}

	@Override
	protected void init() {
		super.init();
		if (!closing)
			minecraft.mainWindow.setGuiScale(3.0d);
	}

	@Override
	public void resize(Minecraft mc, int width, int height) {
		super.resize(mc, width, height);
	}

	@Override
	public void onClose() {
		super.onClose();
		closing = true;
		minecraft.mainWindow.setGuiScale(minecraft.mainWindow.calcGuiScale(minecraft.gameSettings.guiScale, minecraft.getForceUnicodeFont()));
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
		if(scale > 0)
			this.itemScale = scale;
	}

	@Override
	public void render(int mouseX, int mouseY, float p3) {
		if (ColorStyles.getStyle() == Style.vanilla)
			renderBackground();
		else
			fillGradient(6, 6, width - 6, height - 6, -1072689136, -804253680);

		Color color = ColorStyles.getMainColor();

		// Frame
		GuiUtils.drawFrame(5, 5, width - 5, height - 5, 1, color);
		// nice if part of "drawFrame"

		// GUI Title
		drawCenteredString(font, getTitle().getFormattedText(), width / 2, 9, color.getColor());
		int sWidthHalf = font.getStringWidth(getTitle().getFormattedText()) / 2 + 3;
		// Title underline
		AbstractGui.fill(width / 2 - sWidthHalf, 20, width / 2 + sWidthHalf, 21, color.getColor());

		// Item Name
		drawCenteredString(font, editing.getItemStack().getDisplayName().getFormattedText(), width / 2, 27,
				color.getColor());
		
		
		// renders buttons, if there are any
		super.render(mouseX, mouseY, p3);


		// Item (Tooltip must render last or colors will be messed up)
		if(renderItem) {
			GlStateManager.scalef(itemScale, itemScale, 1f);
			RenderHelper.enableGUIStandardItemLighting();
			itemRenderer.renderItemIntoGUI(editing.getItemStack(), (int) (width / (2 * itemScale) - 8),
					(int) (height / (2 * itemScale + 2) - 15));

			GlStateManager.scalef(1f / itemScale, 1f / itemScale, 1f);
			// Item frame
			GuiUtils.drawFrame(width / 2 - 18, height / 3 - 32, width / 2 + 18, height / 3 + 5, 1, color); // Add fill to
																											// "drawframe"
			// Item tooltip
			if (GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 16, height / 3 - 30, 32, 32)) {
				renderTooltip(editing.getItemStack(), mouseX, mouseY);
			}
		}
	}
}
