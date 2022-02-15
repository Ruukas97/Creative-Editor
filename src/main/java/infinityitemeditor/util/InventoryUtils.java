package infinityitemeditor.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {
    public static int countItem(Inventory inventory, Item item) {
        int count = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.getItem() == item) {
                count++;
            }
        }
        return count;
    }


    public static int getEmptySlotsCount(Inventory inventory) {
        int count = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.isEmpty())
                count++;
        }
        return count;
    }


    public static int getEmptySlot(Inventory inventory) {
        int count = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.isEmpty())
                break;
            count++;
        }

        if (count <= 8) {
            count += 36;
        } else if (36 <= count && count <= 39) {
            count = 8 - (count % 4);
        } else if (count == 40) {
            count = 45;
        }
        return count;
    }
}
