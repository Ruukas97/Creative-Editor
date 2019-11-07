package ruukas.editor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import ruukas.editor.nbt.NBTItemBase;
import ruukas.editor.util.ColorUtils;
import ruukas.editor.util.ColorUtils.Color;
import ruukas.editor.util.GuiUtils;

public class MainScreen extends ParentScreen {
	private static int renderTicks = 0;

	public MainScreen(Screen lastScreen, NBTItemBase editing) {
		super(new StringTextComponent(I18n.format("gui.creativeeditor")), lastScreen, editing);
	}

	@Override
	public void render(int mouseX, int mouseY, float p3) {
		super.render(mouseX, mouseY, p3);
		
		boolean vanillaStyle = true;
		Color color = vanillaStyle ? new Color(255, 200, 200, 200) : new Color(255, ColorUtils.hsvToRGB(renderTicks / 5000f, 1f, 1f));

		if(vanillaStyle)
			renderBackground();
		else
			fillGradient(6, 6, width-6, height-6, -1072689136, -804253680);

		//Frame
        GuiUtils.drawFrame(5, 5, width-5, height-5, 1, color);
        //fill(6, 6, width-6, height-6, (int)235 << 24); //Fill in the frame, might be nice if part of "drawFrame"
        
        //First vertical line
        fill(width/3, 20, width/3+1, height-20, color.getColor());
        //Second vertical line
        fill(width*2/3, 20, width*2/3+1, height-20, color.getColor());
        //Left horizontal line
        fill(20, 40, width/3-15, 41, color.getColor());
        fill(width*2/3+16, 40, width-20, 41, color.getColor());


        //GUI Title
		drawCenteredString(font, getTitle().getFormattedText(), width / 2, 9, color.getColor());
		int sWidthHalf = font.getStringWidth(getTitle().getFormattedText())/2 + 3;
		//Title underline
		AbstractGui.fill(width/2-sWidthHalf, 20, width/2+sWidthHalf, 21, color.getColor());
		

		//Item Name
		drawCenteredString(font, editing.getItemStack().getDisplayName().getFormattedText(), width / 2, 27, color.getColor());
		
		//Item
		float itemScale = 2f;
		GlStateManager.scalef(itemScale, itemScale, 1f);
		itemRenderer.renderItemIntoGUI(editing.getItemStack(), (int)(width / (2 * itemScale) - 8), (int) (height/(2 * itemScale) - 8));
		GlStateManager.scalef(1f/itemScale, 1f/itemScale, 1f);
		//Item frame
        GuiUtils.drawFrame(width / 2 - 18, height / 2 - 19, width / 2 + 18,  height / 2 + 17, 1, color); //Add fill to "drawframe"

        //Item tooltip
		if(GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 16, height / 2 - 16, 32, 32)) {
			renderTooltip(editing.getItemStack(), mouseX, mouseY);
		}
		
		
        
		renderTicks = (renderTicks + 1) % 5000;
	}
}
