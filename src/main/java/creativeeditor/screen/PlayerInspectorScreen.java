package creativeeditor.screen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerInspectorScreen extends ParentScreen {
    private final PlayerEntity target;
    private String playerUUID;
    private MinecraftName[] prevNamesUndated;
    private ArrayList<MinecraftNameDated> prevNames;
    private static final HashMap<String, ArrayList<MinecraftNameDated>> cashedNames = new HashMap<String, ArrayList<MinecraftNameDated>>();
    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");


    private static class MinecraftName {
        private String name;
        private long changedToAt;
    }
    
    private static class MinecraftNameDated {
		private final String name;
    	private final String date;
    	public MinecraftNameDated(MinecraftName undatedName) {
			name = undatedName.name;
			if (undatedName.changedToAt == 0) {
				date = "the beginning of time";
			}
			else {
				Date dateObject = new Date( undatedName.changedToAt );
				date = dateFormat.format( dateObject );
			}
		}
    }


    public PlayerInspectorScreen(Screen lastScreen, PlayerEntity targetPlayer) {
        super( new TranslationTextComponent( "gui.playerinspector" ), lastScreen );
        this.target = targetPlayer;
    }


    @Override
    protected void init() {
        super.init();
        playerUUID = "b712317a9e3649e9b8e8dbbae7b3fc02"; // This is Ruukas' uuid // PlayerEntity.getUUID( target.getGameProfile() ).toString();
        prevNames = new ArrayList<MinecraftNameDated>();
        if (cashedNames.containsKey( playerUUID )) {
        	System.out.println("already found previous names in cache");
            prevNames = cashedNames.get( playerUUID );
        }
        else {
            try {
                URL namesURL = new URL( String.format( "https://api.mojang.com/user/profiles/%s/names", playerUUID.replace( "-", "" ) ) );
                InputStreamReader reader = new InputStreamReader( namesURL.openStream() );
                Gson gson = new Gson();
                prevNamesUndated = gson.fromJson( reader, MinecraftName[].class );
                
                for (MinecraftName n : prevNamesUndated) {
                	MinecraftNameDated datedName = new MinecraftNameDated( n );
                	prevNames.add( datedName );
                }
                cashedNames.put( playerUUID, prevNames );
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
        if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, 16, 16 )) {
            Minecraft mc = getMinecraft();
            DataItem headItem = new DataItem( Items.PLAYER_HEAD );
            headItem.getTag().getSkullOwner().set( target.getGameProfile() );
            mc.playerController.sendSlotPacket( headItem.getItemStack(), InventoryUtils.getEmptySlot( mc.player.inventory ) );
            return true;
        }


        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        super.mainRender( mouseX, mouseY, p3, color );

        // Render player model
        InventoryScreen.drawEntityOnScreen( width / 2, height / 2, 50, width / 2f - mouseX, height / 3f - mouseY, target );

        // Render player UUID
        int y = height - 16;
        int x = 9;
        FontRenderer usedFont = minecraft.fontRenderer;
        String uuidText = "Player UUID: " + playerUUID;
        drawString( usedFont, uuidText, x, y, (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, usedFont.getStringWidth( uuidText ), usedFont.FONT_HEIGHT )) ? 0xAAAAAA : 0xFFFFFF );

        // Render previous usernames
        y = 9;
        drawString( usedFont, "Previous usernames:", x, y, 0xFFFFFF);
        y += usedFont.FONT_HEIGHT + 3;
        for (MinecraftNameDated n : prevNames) {
            drawString( usedFont, n.name + " since " + n.date, x, y, 0xFFFFFF );
            y += usedFont.FONT_HEIGHT + 2;
        }

        // Render equipped items and player head
        y = height / 2 - 45;
        x = width / 2 + 100;
        for (ItemStack stack : target.getEquipmentAndArmor()) {
            drawItemStack( stack, x, y, 0, 0, null );

            if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, 16, 16 )) {
                renderTooltip( stack, mouseX, mouseY );

            }

            y -= 15;
        }
        DataItem headItem = new DataItem( Items.PLAYER_HEAD );
        headItem.getTag().getSkullOwner().set( target.getGameProfile() );
        drawItemStack( headItem.getItemStack(), x, y, 0, 0, null );
    }
}
