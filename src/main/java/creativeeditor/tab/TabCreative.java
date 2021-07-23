package creativeeditor.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public abstract class TabCreative extends ItemGroup {
    boolean hasSearchBar = true;

    public TabCreative(String label) {
        super(label);
        setBackgroundImage(new ResourceLocation("item_search.png"));
    }

    public TabCreative setHasSearchBar(boolean has) {
        hasSearchBar = has;
        setBackgroundImage(new ResourceLocation(has ? "item_search.png" : "items.png"));
        return this;
    }


    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }
}
