package creativeeditor.tab;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TabItemList extends TabCreative {
    private final ItemStack[] items;
    private final ItemStack icon;


    public TabItemList(String label, ItemStack icon, ItemStack... items) {
        super( label );
        this.icon = icon;
        this.items = items;
    }


    @Override
    public void fillItemList( NonNullList<ItemStack> items ) {
        for (ItemStack item : this.items) {
            items.add( item );
        }
    }


    @Override
    public ItemStack makeIcon() {
        return icon;
    }
}
