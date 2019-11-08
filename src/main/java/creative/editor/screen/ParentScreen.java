package creative.editor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creative.editor.config.CEStyle;
import creative.editor.nbt.NBTItemBase;
import creative.editor.util.ColorUtils.Color;
import creative.editor.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.ITextComponent;

public class ParentScreen extends Screen {
	protected final Screen lastScreen;
	protected NBTItemBase editing;

	// Back, reset, drop, save button
	boolean hasSave = false;
	boolean closing = false;

	// render itemstack
	boolean renderItemStack = false;
	
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
		int i = minecraft.mainWindow.calcGuiScale(minecraft.gameSettings.guiScale, minecraft.getForceUnicodeFont());
		minecraft.mainWindow.setGuiScale(i);
	}

	@Override
	public boolean keyPressed(int key1, int key2, int key3) {
		return super.keyPressed(key1, key2, key3);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void render(int mouseX, int mouseY, float p3) {
		if (CEStyle.vanillaStyle)
			renderBackground();
		else
			fillGradient(6, 6, width - 6, height - 6, -1072689136, -804253680);

		Color color = CEStyle.color;

		// Frame
		GuiUtils.drawFrame(5, 5, width - 5, height - 5, 1, color);
		// fill(6, 6, width-6, height-6, (int)235 << 24); //Fill in the frame, might be
		// nice if part of "drawFrame"

		// GUI Title
		drawCenteredString(font, getTitle().getFormattedText(), width / 2, 9, color.getColor());
		int sWidthHalf = font.getStringWidth(getTitle().getFormattedText()) / 2 + 3;
		// Title underline
		AbstractGui.fill(width / 2 - sWidthHalf, 20, width / 2 + sWidthHalf, 21, color.getColor());

		// Item Name
		drawCenteredString(font, editing.getItemStack().getDisplayName().getFormattedText(), width / 2, 27,
				color.getColor());

		// Item
		float itemScale = 2f;
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

		// super.render always after bg rendering
		super.render(mouseX, mouseY, p3);
	}
}
