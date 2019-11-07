package ruukas.editor.screen;

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
		Color spectrumColor = new Color(255, ColorUtils.hsvToRGB(renderTicks / 5000f, 1f, 1f));
		
        fill(0, 0, width, height, (int)222 << 24);
        //fill(5, 5, width-5, height-5, spectrumColor + );
        GuiUtils.drawFrame(5, 5, width-5, height-5, 1, spectrumColor);



		drawCenteredString(font, getTitle().getFormattedText(), width / 2, 10, spectrumColor.getColor());
		//int sWidthHalf = font.getStringWidth(getTitle().getFormattedText())/2;
		//AbstractGui.fill(width/2-sWidthHalf, 10, width, x+border, color.getColor());

		itemRenderer.renderItemIntoGUI(editing.getItemStack(), width / 2 - 8, 25);
		
		if(GuiUtils.isMouseIn(mouseX, mouseY, width / 2 - 8, 25, 16, 16)) {
			renderTooltip(editing.getItemStack(), mouseX, mouseY);
		}
		
        
		renderTicks = (renderTicks + 1) % 5000;
	}
}
