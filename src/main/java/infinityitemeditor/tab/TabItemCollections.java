package infinityitemeditor.tab;

import infinityitemeditor.saving.DataItemCollection;
import infinityitemeditor.saving.SaveService;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class TabItemCollections extends TabCreative {
    public TabItemCollections() {
        super("ItemCollections");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Blocks.ENDER_CHEST);
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> stackList) {
        super.fillItemList(stackList);
        List<DataItemCollection> collections = SaveService.getInstance().getItemCollections();
        for (DataItemCollection collection : collections) {
            stackList.add(collection.toShulker().getItemStack());
        }
    }
}
