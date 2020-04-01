package creativeeditor.creativetabs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.GameData;

public class TabUnavailable extends ItemGroup {

    public TabUnavailable() {
        super( "unavailable" );
    }


    @Override
    public ItemStack createIcon() {
        return new ItemStack( Items.BARRIER );
    }


    @Override
    public void fill( NonNullList<ItemStack> items ) {
        // super.fill(items); super sees if any items are assigned to this tab - this
        // tab by definition list items that aren't assigned any tabs

        GameData.getWrapper( Item.class ).forEach( i -> {
            if (i != Items.AIR && (i.getCreativeTabs().isEmpty() || (i.getCreativeTabs().size() == 1 && i.getCreativeTabs().contains( null )))) {
                items.add( new ItemStack( i ) );
            }
        } );
    }

}
