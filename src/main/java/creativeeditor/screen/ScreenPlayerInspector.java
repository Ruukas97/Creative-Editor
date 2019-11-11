package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenPlayerInspector extends ParentScreen {
	private PlayerEntity target;
	
	public ScreenPlayerInspector(Screen lastScreen, PlayerEntity targetPlayer) {
		super(new TranslationTextComponent("gui.playerinspector"), lastScreen);
		this.target = targetPlayer;
	}
	
	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
		
		//Render player name somewhere
		//Try typing draw underneath here to see some options
	}
}
