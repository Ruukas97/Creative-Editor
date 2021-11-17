package infinityitemeditor.util;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataColor;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;
import net.minecraft.tileentity.LockableLootTileEntity;

import java.util.Arrays;

public class ItemUtils {
    public static DataColor getColorTag(DataItem dataItem) {
        final Item item = dataItem.getItem().getItem();
        if (isPotion(dataItem)) {
            return dataItem.getTag().getPotionColor();
        }
        else if (item instanceof FilledMapItem) {
            return dataItem.getTag().getDisplay().getMapColor();
        }
        return dataItem.getTag().getDisplay().getColor();
    }


    /**
     * @see net.minecraft.client.renderer.color.ItemColors
     */
    public static boolean isColorable(DataItem dataItem){
        Item item = dataItem.getItem().getItem();
        return item instanceof IDyeableArmorItem || hasCustomPotionEffects(dataItem) || item == Items.FILLED_MAP;
    }

    public static boolean isPotion(DataItem dataItem){
        return dataItem.getItem().getItem() instanceof PotionItem;
    }

    public static boolean hasCustomPotionEffects(DataItem dataItem){
        return isPotion(dataItem) || dataItem.getItem().getItem() instanceof TippedArrowItem;
    }

    public static boolean hasEffects(DataItem dataItem){
        return dataItem.getItem().getItem() instanceof SuspiciousStewItem;
    }

    public static boolean isLockableItem(DataItem dataItem) {
        final Item item = dataItem.getItem().getItem();
        if (item instanceof BlockItem) {
            BlockItem b = (BlockItem) item;
            return b.getBlock().createTileEntity(b.getBlock().defaultBlockState(), null) instanceof LockableLootTileEntity;
        }
        return false;
    }

    public static boolean isEntityItem(DataItem dataItem) {
        final Item item = dataItem.getItem().getItem();
        return item instanceof SpawnEggItem || item instanceof ItemFrameItem || item instanceof ArmorStandItem || item instanceof MinecartItem;
    }

    public static boolean isBlockItem(DataItem dataItem){
        return dataItem.getItem().isBlockItem();
    }

    public static boolean isTool(DataItem dataItem){
        return dataItem.getItem().getItem() instanceof BlockItem;
    }

    public static boolean isEnchantable(DataItem dataItem){
        final Item item = dataItem.getItem().getItem();
        return dataItem.getItem().getItem().isEnchantable(dataItem.getItemStack()) || Arrays.stream(EnchantmentType.values()).anyMatch(enchantmentType -> enchantmentType.canEnchant(item));
    }

    public static boolean isEnchantmentStorage(DataItem dataItem){
        final Item item = dataItem.getItem().getItem();
        return item instanceof BookItem || item instanceof EnchantedBookItem;
    }
}
