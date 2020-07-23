package creativeeditor.tab;

import java.util.ArrayList;

import creativeeditor.json.CachedHead;
import creativeeditor.json.MinecraftHeads;
import creativeeditor.json.MinecraftHeadsCategory;
import creativeeditor.util.RandomUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

public class TabHead extends TabCreative {
    public static ItemStack fallbackIcon = new ItemStack( Items.PLAYER_HEAD );
    public boolean iconLoaded = false;
    public MinecraftHeadsCategory category;


    public TabHead(MinecraftHeadsCategory category) {
        super( category.getName() );
        this.category = category;
    }


    @Override
    public ItemStack createIcon() {
        ArrayList<CachedHead> heads = MinecraftHeads.getHeads( category );
        CachedHead cached = RandomUtils.getRandomElement( heads );
        cached.loadTexture();
        ItemStack head = cached.getItemStack();
        return head != null ? head.copy() : fallbackIcon;
    }


    @Override
    public ItemStack getIcon() {
        if (iconLoaded) {
            return super.getIcon();
        }

        if (MinecraftHeads.isLoaded( category )) {
            iconLoaded = true;
            return super.getIcon();
        }

        return fallbackIcon;
    }


    @Override
    public void fill( NonNullList<ItemStack> items ) {
        items.addAll( MinecraftHeads.createItemStacks( category ) );
    }


    @Override
    public String getTranslationKey() {
        return category.getTranslationKey();
    }
}
