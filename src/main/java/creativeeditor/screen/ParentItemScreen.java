package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.util.ColorUtils.Color;
import creativeeditor.data.DataItem;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

public class ParentItemScreen extends ParentScreen {
	protected DataItem item;

	// Back, reset, drop, save button (has essential buttons)
	protected boolean hasEssButtons = true;

	// render item
	private boolean renderItem = true;
	private float itemScale = 2.0f;

	// render tooltip top left
	boolean renderToolTip = false;

	public ParentItemScreen(ITextComponent title, Screen lastScreen, DataItem editing) {
		super(title, lastScreen);
		this.item = editing;
	}

	@Override
	protected void init() {
		super.init();
		buttons.clear();
		children().clear();
		renderWidgets.clear();

		if (hasEssButtons) {
			int bwidth = 50;
			int posX = width / 2 - (bwidth / 2);
			int posY = height / 7 * 6;
			String butCloseBack = lastScreen == null ? "gui.main.close" : "gui.main.back";
			
			
			addButton(new StyledButton(posX - bwidth - 1, posY, bwidth, 20, I18n.format(butCloseBack), b -> {
				mc.displayGuiScreen(lastScreen);
			}));
			addButton(new StyledButton(posX, posY - 11, bwidth, 20, I18n.format("gui.main.reset"), b -> {
				item.setCount(0);
				item.getItemNBTTag().clear();
				this.init();
			}));
			addButton(new StyledButton(posX, posY + 10, bwidth, 20, I18n.format("gui.main.save"), b -> {
				mc.playerController.sendSlotPacket(item.getItemStack(), 36 + mc.player.inventory.currentItem);
			}));
			addButton(new StyledButton(posX + bwidth + 1, posY, bwidth, 20, I18n.format("gui.main.drop"), b -> {
				mc.playerController.sendPacketDropItem(item.getItemStack());
				// Shift for /give
			}));
		}
	}
	
	public DataItem getItem() {
		return this.item;
	}

	public void setRenderItem(boolean shouldRender, float scale) {
		this.renderItem = shouldRender;
		if (scale > 0)
			this.itemScale = scale;
	}

	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
	}

	@Override
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		super.overlayRender(mouseX, mouseY, p3, color);
		// Item (Tooltip must render last or colors will be messed up)
		if (renderItem) {
			GlStateManager.scalef(itemScale, itemScale, 1f);
			RenderHelper.enableGUIStandardItemLighting();
			int x = (int) (width / (2 * itemScale) - 8);
			int y = (int) (30 / itemScale + height / (2 * itemScale) - 8);
			itemRenderer.renderItemIntoGUI(item.getItemStack(), x, y);
			itemRenderer.renderItemOverlayIntoGUI(font, item.getItemStack(), x, y, null);
			RenderHelper.disableStandardItemLighting();

			GlStateManager.scalef(1f / itemScale, 1f / itemScale, 1f);

			// Item frame
			GuiUtils.drawFrame(width / 2 - 20, height / 2 + 10, width / 2 + 20, height / 2 + 50, 1, color);

			if (GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 17, height / 2 + 13, 34, 34)) {
				renderTooltip(item.getItemStack(), mouseX, mouseY);
			}
		}
	}
}
