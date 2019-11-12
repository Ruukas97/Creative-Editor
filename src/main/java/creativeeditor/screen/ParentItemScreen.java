package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.nbt.NBTItemBase;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtils;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

public class ParentItemScreen extends ParentScreen {
	protected NBTItemBase editing;

	// Back, reset, drop, save button (has essential buttons)
	protected boolean hasEssButtons = true;

	// render item
	private boolean renderItem = true;
	private float itemScale = 2.0f;

	// render tooltip top left
	boolean renderToolTip = false;

	public ParentItemScreen(ITextComponent title, Screen lastScreen, NBTItemBase editing) {
		super(title, lastScreen);
		this.editing = editing;
	}

	@Override
	protected void init() {
		super.init();

		if (hasEssButtons) {
			int bwidth = 50;
			int posX = width / 2 - (bwidth / 2);
			int posY = height / 7 * 6;
			String butCloseBack = lastScreen == null ? "gui.main.close" : "gui.main.back";
			
			
			addButton(new CEWButton(posX - bwidth - 1, posY, bwidth, 20, I18n.format(butCloseBack), b -> {
				mc.displayGuiScreen(lastScreen);
			}));
			addButton(new CEWButton(posX, posY - 11, bwidth, 20, I18n.format("gui.main.reset"), b -> {
				editing.setTag(null);
			}));
			addButton(new CEWButton(posX, posY + 10, bwidth, 20, I18n.format("gui.main.save"), b -> {
				mc.playerController.sendSlotPacket(editing.getItemStack(), 36 + mc.player.inventory.currentItem);
			}));
			addButton(new CEWButton(posX + bwidth + 1, posY, bwidth, 20, I18n.format("gui.main.drop"), b -> {
				mc.playerController.sendPacketDropItem(editing.getItemStack());
				// Shift for /give
			}));
		}
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
			int y = (int) (height / (2 * itemScale) - 8);
			itemRenderer.renderItemIntoGUI(editing.getItemStack(), x, y);
			itemRenderer.renderItemOverlayIntoGUI(font, editing.getItemStack(), x, y, null);

			GlStateManager.scalef(1f / itemScale, 1f / itemScale, 1f);

			// Item frame
			GuiUtils.drawFrame(width / 2 - 20, height / 2 - 20, width / 2 + 20, height / 2 + 20, 1, color);

			if (GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 17, height / 2 - 17, 34, 34)) {
				renderTooltip(editing.getItemStack(), mouseX, mouseY);
			}
		}
	}
}
