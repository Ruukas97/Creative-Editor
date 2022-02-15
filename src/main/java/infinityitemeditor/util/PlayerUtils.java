package infinityitemeditor.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class PlayerUtils {
    @SuppressWarnings("resource")
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    public static Inventory getLocalInventory() {
        return getLocalPlayer().getInventory();
    }


    public static void switchGamemode() {

    }
}
