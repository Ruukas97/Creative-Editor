package creativeeditor.tab;

import creativeeditor.data.DataItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TabLoadedTileEntities extends TabCreative {

    public TabLoadedTileEntities() {
        super( "tileentities" );
    }


    @Override
    public void fill( NonNullList<ItemStack> items ) {
        Minecraft mc = Minecraft.getInstance();
        ClientWorld world = mc.world;
        for (TileEntity te : world.loadedTileEntityList) {
            BlockState state = te.getBlockState();
            Item item = state.getBlock().asItem();
            DataItem dItem = new DataItem( item, 1, te, 0 );
            ItemStack stack = dItem.getData();
            boolean found = false;;
            for(ItemStack ex : items) {
                if(ex.equals( stack, false )) {
                    found = true;
                    if(ex.getCount() < 64)
                        ex.setCount( ex.getCount() + 1 );
                    break;
                }
            }
            if(!found) {
                items.add( stack );
            }
        }
    }


    @Override
    public ItemStack createIcon() {
        return new ItemStack( Items.LIME_SHULKER_BOX );
    }
}
