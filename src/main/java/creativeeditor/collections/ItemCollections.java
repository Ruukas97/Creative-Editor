package creativeeditor.collections;

import java.util.ArrayList;

import creativeeditor.tab.TabHead;
import lombok.Getter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.GameData;

public class ItemCollections implements Iterable<ItemCollection> {
    public static final ItemCollections INSTANCE = new ItemCollections();
    // TODO hotbar

    @Getter
    private ArrayList<ItemCollection> collections;


    public ItemCollections() {
        collections = new ArrayList<>();

        collections.add(new ItemCollection() {
            @Override
            public String getName() {
                return I18n.format("itemGroup.all");
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public ItemStack getIcon() {
                return new ItemStack(Items.CAKE);
            }

            @Override
            public boolean hasSearchBar() {
                return true;
            }

            @Override
            public void fill(NonNullList<ItemStack> items) {
                GameData.getWrapper( Item.class ).forEach(i -> {
                    if (i != Items.AIR) {
                        items.add( new ItemStack( i ) );
                    }
                } );
            }
        });

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
