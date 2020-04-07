package creativeeditor.tab;

import net.minecraft.item.ItemGroup;

public abstract class TabCreative extends ItemGroup {

    public TabCreative(String label) {
        super( label );
        setBackgroundImageName( "item_search.png" );
    }


    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
