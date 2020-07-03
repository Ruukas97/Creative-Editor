package creativeeditor.collections;

import java.util.ArrayList;

import creativeeditor.tab.TabHead;
import lombok.Getter;
import net.minecraft.item.ItemGroup;

public class ItemCollections implements Iterable<ItemCollection> {
    public static final ItemCollections INSTANCE = new ItemCollections();
    // TODO hotbar

    @Getter
    private ArrayList<ItemCollection> collections;


    public ItemCollections() {
        collections = new ArrayList<>();
        for (int i = 0; i < ItemGroup.getGroupCountSafe(); i++) {
            ItemGroup group = ItemGroup.GROUPS[i];
            if (group != null && group != ItemGroup.SEARCH && group != ItemGroup.INVENTORY && group != ItemGroup.HOTBAR && !(group instanceof TabHead)) {
                collections.add( new ItemGroupCollection( group ) );
            }
        }
    }


    @Override
    public java.util.Iterator<ItemCollection> iterator() {
        return collections.iterator();
    }
}
