package infinityitemeditor.tab;

import infinityitemeditor.json.CachedHead;
import infinityitemeditor.json.MinecraftHeads;
import infinityitemeditor.json.MinecraftHeadsCategory;
import infinityitemeditor.util.RandomUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class TabHead extends TabCreative {
    public static ItemStack fallbackIcon = new ItemStack(Items.PLAYER_HEAD);
    public boolean iconLoaded = false;
    public MinecraftHeadsCategory category;


    public TabHead(MinecraftHeadsCategory category) {
        super(category.getName());
        this.category = category;
    }


    @Override
    public ItemStack makeIcon() {
        ArrayList<CachedHead> heads = MinecraftHeads.getHeads(category);
        CachedHead cached = RandomUtils.getRandomElement(heads);
        cached.loadTexture();
        ItemStack head = cached.getItemStack();
        return head != null ? head.copy() : fallbackIcon;
    }


    @Override
    public ItemStack getIconItem() {
        if (iconLoaded) {
            return super.getIconItem();
        }

        if (MinecraftHeads.isLoaded(category)) {
            iconLoaded = true;
            return super.getIconItem();
        }

        return fallbackIcon;
    }


    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        items.addAll(MinecraftHeads.createItemStacks(category));
    }


//    @Override
//    public MutableComponent getDisplayName() {
//        return category.getTranslationKey();
//    }
}
