package creativeeditor.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

@AllArgsConstructor
public class ItemGroupCollection implements ItemCollection {

    @Getter
    private ItemGroup group;


    @Override
    public String getName() {
        return I18n.get(group.getDisplayName().getString());
    }


    @Override
    @Nullable
    public String getDescription() {
        return null;
    }


    @Override
    public ItemStack getIcon() {
        return group.getIconItem();
    }


    @Override
    public boolean hasSearchBar() {
        return group.hasSearchBar();
    }


    @Override
    public void fill(NonNullList<ItemStack> items) {
        group.fillItemList(items);
    }
}
