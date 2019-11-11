package creativeeditor.screen;

import com.google.common.math.Quantiles.Scale;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
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
		InventoryScreen.drawEntityOnScreen(width/2, height/2, 50, width/2 - mouseX, height/3 - mouseY, target);
		
		String playerName = target.getDisplayName().getString();
		String playerUUID = PlayerEntity.getUUID(target.getGameProfile()).toString();
		
		
		drawCenteredString(mc.fontRenderer, playerName, width/2, height/2 - 110, 0xFFFFFF);
		drawCenteredString(mc.fontRenderer, playerUUID, width/2, height/2 + 8, 0xFFFFFF);
		
		ItemStack[] stacks = new ItemStack[6];
		int i = 0;
		int y = height/2;
		for ( ItemStack stack : target.getEquipmentAndArmor() ) 
		{
		    stacks[i++] = stack;
		    y -= 15;
		    itemRenderer.renderItemIntoGUI(stack, width/2 + 125, y);
		}
		
		
	}
}
