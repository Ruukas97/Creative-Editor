package creativeeditor.collections;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface ItemCollection {
    String getName();
    String getDescription();
    ItemStack getIcon();
    boolean hasSearchBar();
    void fill(NonNullList<ItemStack> items);
}
