package infinityitemeditor.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

@AllArgsConstructor
public class CreativeModeTabCollection implements ItemCollection {

    @Getter
    private CreativeModeTab group;


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
