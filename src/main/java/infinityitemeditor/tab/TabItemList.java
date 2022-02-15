package infinityitemeditor.tab;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class TabItemList extends TabCreative {
    private final ItemStack[] items;
    private final ItemStack icon;


    public TabItemList(String label, ItemStack icon, ItemStack... items) {
        super(label);
        this.icon = icon;
        this.items = items;
    }


    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        for (ItemStack item : this.items) {
            items.add(item);
        }
    }


    @Override
    public ItemStack makeIcon() {
        return icon;
    }
}
