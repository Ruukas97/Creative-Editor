package creativeeditor.tab;

import java.util.ArrayList;

import creativeeditor.json.CachedHead;
import creativeeditor.json.MinecraftHeads;
import creativeeditor.json.MinecraftHeadsCategory;
import creativeeditor.util.RandomUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class TabHead extends TabCreative {
    public static ItemStack fallbackIcon = new ItemStack( Items.PLAYER_HEAD );
    public boolean iconLoaded = false;
    public MinecraftHeadsCategory category;


    public TabHead(MinecraftHeadsCategory category) {
        super( category.getName() );
        this.category = category;
    }


    @Override
    public ItemStack makeIcon() {
        ArrayList<CachedHead> heads = MinecraftHeads.getHeads( category );
        CachedHead cached = RandomUtils.getRandomElement( heads );
        cached.loadTexture();
        ItemStack head = cached.getItemStack();
        return head != null ? head.copy() : fallbackIcon;
    }


    @Override
    public ItemStack getIconItem() {
        if (iconLoaded) {
            return super.getIconItem();
        }

        if (MinecraftHeads.isLoaded( category )) {
            iconLoaded = true;
            return super.getIconItem();
        }

        return fallbackIcon;
    }


    @Override
    public void fillItemList( NonNullList<ItemStack> items ) {
        items.addAll( MinecraftHeads.createItemStacks( category ) );
    }


    @Override
    public ITextComponent getDisplayName() {
        return category.getTranslationKey();
    }
}
