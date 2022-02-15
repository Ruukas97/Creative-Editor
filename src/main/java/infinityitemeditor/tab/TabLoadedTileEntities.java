package infinityitemeditor.tab;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TabLoadedTileEntities extends TabCreative {

    public TabLoadedTileEntities() {
        super("tileentities");
    }


    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        Minecraft mc = Minecraft.getInstance();
        ClientWorld world = mc.level;
        for (TileEntity te : world.blockEntityList) {
            if (InventoryUtils.getEmptySlotsCount(mc.player.inventory) == 0) {
                break;
            }

            /*mc.player.connection.getNBTQueryManager().queryTileEntity(te.getPos(), (nbt) -> {
                if(InventoryUtils.getEmptySlotsCount( mc.player.inventory ) > 0) {
                    te.getTileData().merge( nbt );
                    BlockState state = te.getBlockState();
                    Item item = state.getBlock().asItem();
                    DataItem dItem = new DataItem( item, 1, te, 0 );
                    ItemStack stack = dItem.getItemStack();
                    items.add(stack );
                    mc.playerController.sendSlotPacket( stack, InventoryUtils.getEmptySlot( mc.player.inventory ) );
                }
             });*/
        }

        NonNullList<ItemStack> stands = NonNullList.create();
        for (Entity ent : world.entitiesForRendering()) {
            if (ent instanceof ArmorStand) {
                ArmorStand stand = (ArmorStand) ent;
                CompoundTag itemTag = new CompoundTag();
                CompoundTag entityTag = new CompoundTag();
                stand.save(entityTag);
                itemTag.put(NBTKeys.keys.tagEntityTag(), entityTag);

                DataItem dItem = new DataItem(Items.ARMOR_STAND, 1, itemTag, 0);

                ItemStack stack = dItem.getData();
                boolean found = false;
                for (ItemStack ex : stands) {
                    if (ex.equals(stack, false)) {
                        found = true;
                        if (ex.getCount() < 64)
                            ex.setCount(ex.getCount() + 1);
                        break;
                    }
                }
                if (!found) {
                    stands.add(stack);
                }
            }
        }

        items.addAll(stands);
    }


    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.LIME_SHULKER_BOX);
    }
}
