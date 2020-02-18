package creativeeditor.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

public class PlayerUtils {
	public static PlayerEntity getLocalPlayer() {
		return Minecraft.getInstance().player;
	}
	
	public static PlayerInventory getLocalPlayerInventory() {
		return getLocalPlayer().inventory;
	}
	
	public static void switchGamemode() {
		
	}
}
