package infinityitemeditor.tab;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

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
            if (ent instanceof ArmorStandEntity) {
                ArmorStandEntity stand = (ArmorStandEntity) ent;
                CompoundNBT itemTag = new CompoundNBT();
                CompoundNBT entityTag = new CompoundNBT();
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
