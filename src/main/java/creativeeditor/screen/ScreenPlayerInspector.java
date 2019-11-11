package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.TextComponentMessageFormatHandler;

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
		InventoryScreen.drawEntityOnScreen(width/2, height/2, 50, width/2 - mouseX, height/3 - mouseY, target);
		mc.fontRenderer.drawStringWithShadow(target.getDisplayName().getString(), width/2 - 15, height/2 + 5, 1);
	}
}
