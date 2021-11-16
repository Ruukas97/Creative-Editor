package infinityitemeditor.events;

import infinityitemeditor.data.DataItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerInteractHandler {

    public PlayerInteractHandler() {
        this.minecraft = Minecraft.getInstance();
    }

    private final Minecraft minecraft;

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteractSpecific e) {
        DataItem heldItemStack = new DataItem(e.getPlayer().getMainHandItem());
        Item heldItem = e.getPlayer().getMainHandItem().getItem();
        if (e.getPlayer().isCreative() && e.getTarget().getType() == EntityType.PLAYER && e.getPlayer().isShiftKeyDown() && (heldItem == Items.PLAYER_HEAD) && heldItemStack.getTag().getSkullOwner().get() == null) {
            heldItemStack.getTag().getSkullOwner().set(((PlayerEntity) e.getTarget()).getGameProfile());
            minecraft.getConnection().send(new CCreativeInventoryActionPacket(36 + minecraft.player.inventory.selected, heldItemStack.getItemStack()));
        }
    }

}
