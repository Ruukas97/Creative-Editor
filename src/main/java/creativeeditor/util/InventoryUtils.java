package creativeeditor.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
    public static int countItem( PlayerInventory inventory, Item item ) {
        int count = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.getItem() == item) {
                count++;
            }
        }
        return count;
    }


    public static int getEmptySlotsCount( PlayerInventory inventory ) {
        int count = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.isEmpty())
                count++;
        }
        return count;
    }


    public static int getEmptySlot( PlayerInventory inventory ) {
        int count = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.isEmpty())
                break;
            count++;
        }

        if (count <= 8) {
            count += 36;
        }
        else if (36 <= count && count <= 39) {
            count = 8 - (count % 4);
        }
        else if (count == 40) {
            count = 45;
        }
        return count;
    }
}
