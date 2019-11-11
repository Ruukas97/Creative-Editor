package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.nbt.NBTItemBase;
import creativeeditor.util.GuiUtils;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.CEWButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.ITextComponent;

public class ParentItemScreen extends ParentScreen {
	protected NBTItemBase editing;

	// Back, reset, drop, save button (has essential buttons)
	protected boolean hasEssButtons = false;

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
	}
	
	public void setRenderItem(boolean shouldRender, float scale) {
		this.renderItem = shouldRender;
		if (scale > 0)
			this.itemScale = scale;
	}
	
	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
		
		// Item Name
		String itemCount = editing.getCount() > 1 ? editing.getCount() + "x " : "";
		String itemOverview = itemCount + editing.getItemStack().getDisplayName().getFormattedText();
		drawCenteredString(font, itemOverview, width / 2, 27,
				color.getInt());

		buttons.forEach(b -> {
			b.render(mouseX, mouseY, p3);
		});
	}
	
	@Override
	public void overlayRender(int mouseX, int mouseY, float p3, Color color) {
		super.overlayRender(mouseX, mouseY, p3, color);
		
		// Item (Tooltip must render last or colors will be messed up)
		if (renderItem) {
			GlStateManager.scalef(itemScale, itemScale, 1f);
			RenderHelper.enableGUIStandardItemLighting();
			int x = (int) (width / (2 * itemScale) - 8);
			int y = (int) (height / (2 * itemScale + 2) - 15);
			itemRenderer.renderItemIntoGUI(editing.getItemStack(), x, y);
			itemRenderer.renderItemOverlayIntoGUI(font, editing.getItemStack(), x, y, null);

			GlStateManager.scalef(1f / itemScale, 1f / itemScale, 1f);
			
			// Item frame
			GuiUtils.drawFrame(width / 2 - 18, height / 3 - 33, width / 2 + 18, height / 3 + 3, 1, color);
			
			if (GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 17, height / 3 - 32, 34, 34)) {
				renderTooltip(editing.getItemStack(), mouseX, mouseY);
			}
		}
	}
}
