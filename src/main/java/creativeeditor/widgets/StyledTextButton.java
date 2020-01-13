package creativeeditor.widgets;

import creativeeditor.styles.StyleManager;
import net.minecraft.client.Minecraft;

public class StyledTextButton extends StyledButton {
	public StyledTextButton(int xIn, int yIn, int stringWidth, String msg, IPressable onPress) {
		super(xIn - (stringWidth / 2) - 1, yIn - 7, stringWidth + 2, 9, msg, onPress);
	}

	@Override
	public void renderButton(int mouseX, int mouseY, float unused) {
		if (this.visible)
			drawString(Minecraft.getInstance().fontRenderer, getMessage(), x + 1, y + 1,
					StyleManager.getCurrentStyle().getFGColor(this).getInt());
	}
}
