package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorstandScreen extends ParentScreen {

	public ArmorstandScreen(ITextComponent title, Screen lastScreen) {
		super(new TranslationTextComponent("gui.armorstand"), lastScreen);
	}
	
	@Override
	protected void init() {
		
		super.init();
	}
	
	@Override
	public void backRender(int mouseX, int mouseY, float p3, Color color) {
		
		super.backRender(mouseX, mouseY, p3, color);
	}
	

}
