package creativeeditor.tab;

import net.minecraft.item.ItemGroup;

public abstract class TabCreative extends ItemGroup {
    boolean hasSearchBar = true;
    
    public TabCreative(String label) {
        super( label );
        setBackgroundImageName( "item_search.png" );
    }
    
    public TabCreative setHasSearchBar(boolean has) {
        hasSearchBar = has;
        setBackgroundImageName( has ? "item_search.png" : "items.png" );
        return this;
    }


    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }
}
