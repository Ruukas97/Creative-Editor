package creativeeditor.collections;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

@AllArgsConstructor
public class ItemGroupCollection implements ItemCollection {

    @Getter
    private final ItemGroup group;


    @Override
    public String getName() {
        return I18n.format( group.getTranslationKey() );
    }


    @Override
    @Nullable
    public String getDescription() {
        return null;
    }


    @Override
    public ItemStack getIcon() {
        return group.getIcon();
    }


    @Override
    public boolean hasSearchBar() {
        return group.hasSearchBar();
    }


    @Override
    public void fill( NonNullList<ItemStack> items ) {
        group.fill( items );
    }
}
