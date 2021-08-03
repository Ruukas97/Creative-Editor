package creativeeditor.tab;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.GameData;

public class TabUnavailable extends TabCreative {

    public TabUnavailable() {
        super("unavailable");
    }


    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.BARRIER);
    }


    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        GameData.getWrapper(Registry.ITEM_REGISTRY, Lifecycle.stable()).forEach(i -> {
            if (i != Items.AIR && (i.getCreativeTabs().isEmpty() || (i.getCreativeTabs().size() == 1 && i.getCreativeTabs().contains(null)))) {
                items.add(new ItemStack(i));
            }
        });
        ItemStack stack = new ItemStack(Items.ITEM_FRAME);
        stack.getOrCreateTagElement("EntityTag").putInt("Invisible", 1);
        String itemName = I18n.get("item.tag.invisible") + " " + I18n.get("item.minecraft.item_frame");
        stack.setHoverName(new StringTextComponent(itemName));
        items.add(stack);
    }
}
