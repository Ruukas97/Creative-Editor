package ruukas.editor.util;

import net.minecraft.client.gui.AbstractGui;
import ruukas.editor.util.ColorUtils.Color;

public class GuiUtils {
	public static boolean isMouseIn(int mouseX, int mouseY, int x, int y, int width, int height) {
		return x <= mouseX && mouseX < x+width && y <= mouseY && mouseY < y+height;
	}
	
	public static void drawFrame(int x, int y, int width, int height, int border, Color color) {
		AbstractGui.fill(x, y, width, y+border, color.getColor());
		AbstractGui.fill(x, y+border, x+border, height-border, color.getColor());
		AbstractGui.fill(width-border, y+border, width, height-border, color.getColor());
		AbstractGui.fill(x, height-border, width, height, color.getColor());
	}
}
