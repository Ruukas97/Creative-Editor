package infinityitemeditor.collections;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface ItemCollection {
    String getName();

    String getDescription();

    ItemStack getIcon();

    boolean hasSearchBar();

    void fill(NonNullList<ItemStack> items);
}
