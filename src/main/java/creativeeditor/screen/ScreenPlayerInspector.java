package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenPlayerInspector extends ParentScreen {
	private PlayerEntity target;
	private String playerUUID;
	
	public ScreenPlayerInspector(Screen lastScreen, PlayerEntity targetPlayer) {
		super(new TranslationTextComponent("gui.playerinspector"), lastScreen);
		this.target = targetPlayer;
	}
	
	@Override
	protected void init() {
		super.init();
		playerUUID = PlayerEntity.getUUID(target.getGameProfile()).toString();
	}
	@Override
	public void mainRender(int mouseX, int mouseY, float p3, Color color) {
		super.mainRender(mouseX, mouseY, p3, color);
		
		//Render player model
		InventoryScreen.drawEntityOnScreen(width/2, height/2, 50, width/2 - mouseX, height/3 - mouseY, target);
		//Render player UUID
		drawCenteredString(mc.fontRenderer, playerUUID, width/2, height/2 + 8, 0xFFFFFF);
		
		//Render equipped items
		ItemStack[] stacks = new ItemStack[6];
		int i = 0;
		int y = height/2 - 30;
		int x = width/2 + 100;
		for ( ItemStack stack : target.getEquipmentAndArmor() ) 
		{
		    stacks[i++] = stack;
		    y -= 15;
		    GlStateManager.scalef(1.25f, 1.25f, 1f);
		    itemRenderer.renderItemIntoGUI(stack, x, y);
		    itemRenderer.renderItemOverlayIntoGUI(font, stack.getStack(), x, y, null);
		    
		    GlStateManager.scalef(.8f, .8f, 1f);
		    if(mouseX >= x*1.25-8 && mouseX <= x*1.25+8 && mouseY <= y*1.25+8 && mouseY >= y*1.25-8)
		    {
		    	renderTooltip(stack, mouseX, mouseY);
		    }
		}
		
	}
	
}
