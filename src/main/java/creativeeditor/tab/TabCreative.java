package creativeeditor.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public abstract class TabCreative extends ItemGroup {
    boolean hasSearchBar = true;
    private final String path = "textures/gui/container/creative_inventory/tab_";
    private final String searchTabPath = path + "item_search.png";
    private final String normalTabPath = path + "items.png";

    public TabCreative(String label) {
        super(label);
        setBackgroundImage(new ResourceLocation(searchTabPath));
    }

    public TabCreative setHasSearchBar(boolean has) {
        hasSearchBar = has;
        setBackgroundImage(new ResourceLocation(has ? searchTabPath : normalTabPath));
        return this;
    }


    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }
}
