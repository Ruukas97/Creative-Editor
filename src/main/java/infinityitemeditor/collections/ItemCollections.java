package infinityitemeditor.collections;

import com.mojang.serialization.Lifecycle;
import infinityitemeditor.tab.TabHead;
import lombok.Getter;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.GameData;

import java.util.ArrayList;

public class ItemCollections implements Iterable<ItemCollection> {
    public static final ItemCollections INSTANCE = new ItemCollections();
    // TODO hotbar

    @Getter
    private final ArrayList<ItemCollection> collections;


    public ItemCollections() {
        collections = new ArrayList<>();

        collections.add(new ItemCollection() {
            @Override
            public String getName() {
                return I18n.get("itemGroup.all");
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
                GameData.getWrapper(Registry.ITEM_REGISTRY, Lifecycle.stable()).forEach(i -> {
                    if (i != Items.AIR) {
                        items.add(new ItemStack(i));
                    }
                });
            }
        });

        for (int i = 0; i < CreativeModeTab.getGroupCountSafe(); i++) {
            CreativeModeTab group = CreativeModeTab.TABS[i];
            if (group != null && group != CreativeModeTab.TAB_SEARCH && group != CreativeModeTab.TAB_INVENTORY && group != CreativeModeTab.TAB_HOTBAR && !(group instanceof TabHead)) {
                collections.add( new CreativeModeTabCollection( group ) );
            }
        }
    }


    @Override
    public java.util.Iterator<ItemCollection> iterator() {
        return collections.iterator();
    }
}
