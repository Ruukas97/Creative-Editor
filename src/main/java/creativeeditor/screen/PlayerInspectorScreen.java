package creativeeditor.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import creativeeditor.util.GuiUtil;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerInspectorScreen extends ParentScreen {
    private PlayerEntity target;
    private String playerUUID;


    public PlayerInspectorScreen(Screen lastScreen, PlayerEntity targetPlayer) {
        super( new TranslationTextComponent( "gui.playerinspector" ), lastScreen );
        this.target = targetPlayer;
    }


    @Override
    protected void init() {
        super.init();
        playerUUID = PlayerEntity.getUUID( target.getGameProfile() ).toString();
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        super.mainRender( mouseX, mouseY, p3, color );

        // Render player model
        // GlStateManager.color3f( 1f, 1f, 1f );
        InventoryScreen.drawEntityOnScreen( width / 2, height / 2, 50, width / 2 - mouseX, height / 3 - mouseY, target );
        // Render player UUID
        drawCenteredString( minecraft.fontRenderer, playerUUID, width / 2, height / 2 + 8, 0xFFFFFF );

        // Render equipped items
        int y = height / 2 - 45;
        int x = width / 2 + 100;
        for (ItemStack stack : target.getEquipmentAndArmor()) {
            drawItemStack( stack, x, y, null );

            if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, 16, 16 )) {
                renderTooltip( stack, mouseX, mouseY );
            }
            
            y -= 15;
        }

    }

}
