package creativeeditor.widgets;

import creativeeditor.styles.Style;
import creativeeditor.styles.Style.ButtonColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class CEWButton extends Button {
	public CEWButton(int x, int y, int width, int height, String text, IPressable onPress) {
		super(x, y, width, height, text, onPress);
	}

	@Override
	public void renderButton(int mouseX, int mouseY, float unused) {
		Style.getCurrentStyle().renderButton(this, mouseX, mouseY, alpha);
	}
	
	@Override
	public void renderBg(Minecraft mc, int mouseX, int mouseY) {
		super.renderBg(mc, mouseX, mouseY);
	}
	
	@Override
	public int getYImage(boolean p_getYImage_1_) {
		return super.getYImage(p_getYImage_1_);
	}

	@Override
	public int getFGColor() {
		ButtonColor bColor = Style.getCurrentStyle().getButtonColor();
		if(this.isHovered)
			return bColor.hovered().getInt();
		if(!this.active)
			return bColor.inactive().getInt();
		return bColor.standard().getInt();
	}
}
