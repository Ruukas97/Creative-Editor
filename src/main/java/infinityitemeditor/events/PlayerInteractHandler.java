package infinityitemeditor.events;

import infinityitemeditor.data.DataItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import infinityitemeditor.screen.ParentItemScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerInteractHandler {

    public PlayerInteractHandler() {
        
    }


    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteractSpecific e) {
        DataItem heldItemStack = new DataItem(e.getPlayer().getMainHandItem());
        Item heldItem = e.getPlayer().getMainHandItem().getItem();
        if (e.getPlayer().isCrouching() && e.getPlayer().isCreative() && e.getTarget().getType() == EntityType.PLAYER && heldItem == Items.PLAYER_HEAD && heldItemStack.getTag().getSkullOwner().get() == null) {
            heldItemStack.getTag().getSkullOwner().set(((PlayerEntity) e.getTarget()).getGameProfile());
            ParentItemScreen.save(heldItemStack);
        }
    }

}
