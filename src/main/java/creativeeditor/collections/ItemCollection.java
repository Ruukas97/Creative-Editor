package creativeeditor.collections;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface ItemCollection {
    public String getName();
    public String getDescription();
    public ItemStack getIcon();
    public boolean hasSearchBar();
    public void fill(NonNullList<ItemStack> items);
}
