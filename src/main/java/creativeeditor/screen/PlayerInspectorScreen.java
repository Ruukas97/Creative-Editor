package creativeeditor.screen;

import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
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
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        int y = height - 16;
        int x = 9;
        FontRenderer usedFont = minecraft.fontRenderer;
        String uuidText = "Player UUID: " + playerUUID;
        if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, usedFont.getStringWidth( uuidText ), usedFont.FONT_HEIGHT )) {
            Minecraft mc = getMinecraft();
            mc.keyboardListener.setClipboardString( playerUUID );
        }


        y = height / 2 - 45;
        x = width / 2 + 100;
        for (ItemStack stack : target.getEquipmentAndArmor()) {
            if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, 16, 16 )) {
                Minecraft mc = getMinecraft();
                mc.playerController.sendSlotPacket( stack, InventoryUtils.getEmptySlot( mc.player.inventory ) );
                return true;
            }

            y -= 15;
        }


        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        super.mainRender( mouseX, mouseY, p3, color );

        // Render player model
        InventoryScreen.drawEntityOnScreen( width / 2, height / 2, 50, width / 2 - mouseX, height / 3 - mouseY, target );

        // Render player UUID
        int y = height - 16;
        int x = 9;
        FontRenderer usedFont = minecraft.fontRenderer;
        String uuidText = "Player UUID: " + playerUUID;
        drawString( usedFont, uuidText, x, y, (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, usedFont.getStringWidth( uuidText ), usedFont.FONT_HEIGHT )) ? 0xAAAAAA : 0xFFFFFF );

        // Render equipped items
        y = height / 2 - 45;
        x = width / 2 + 100;
        for (ItemStack stack : target.getEquipmentAndArmor()) {
            drawItemStack( stack, x, y, null );

            if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, 16, 16 )) {
                renderTooltip( stack, mouseX, mouseY );
                
            }

            y -= 15;
        }
    }
}
