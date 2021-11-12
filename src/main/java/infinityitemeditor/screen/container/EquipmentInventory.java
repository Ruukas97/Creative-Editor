package infinityitemeditor.screen.container;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagItemList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class EquipmentInventory implements IInventory {
    private TagItemList armor;
    private TagItemList hands;
    private TagItemList[] both;

    public EquipmentInventory(TagItemList armor, TagItemList hands) {
        this.armor = armor;
        this.hands = hands;
        this.both = new TagItemList[]{armor, hands};
    }

    @Override
    public int getContainerSize() {
        return armor.getData().length + hands.get().length;
    }

    @Override
    public boolean isEmpty() {
        for (DataItem item : armor.get()) {
            if (!item.isDefault()) {
                return false;
            }
        }
        for (DataItem item : hands.get()) {
            if (!item.isDefault()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        DataItem[] array = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().length) {
                array = itemList.get();
                break;
            }

            index -= itemList.get().length;
        }

        return array == null || array[index] == null ? ItemStack.EMPTY : array[index].getItemStack();
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        DataItem[] array = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().length) {
                array = itemList.get();
                break;
            }

            index -= itemList.get().length;
        }
        if (array != null && array[index] != null && !array[index].getItemStack().isEmpty()) {
            DataItem old = array[index];
            ItemStack is = removeItem(array, index, count);
            int i = old.getCount().get() - is.getCount();
            array[index] = i > 0 ? new DataItem(is.getItem(), i, old.getItemStack().getTag(), 0) : new DataItem();
            return is;
        }
        return ItemStack.EMPTY;
    }


    private static ItemStack removeItem(DataItem[] array, int index, int count) {
        return index >= 0 && index < array.length && !array[index].getItemStack().isEmpty() && count > 0 ? array[index].split(count).getItemStack() : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        DataItem[] array = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().length) {
                array = itemList.get();
                break;
            }

            index -= itemList.get().length;
        }

        if (array != null && array[index] != null && !array[index].getItemStack().isEmpty()) {
            DataItem item = array[index];
            array[index] = new DataItem();
            return item.getItemStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        DataItem[] array = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().length) {
                array = itemList.get();
                break;
            }

            index -= itemList.get().length;
        }

        if (array != null) {
            array[index] = new DataItem(stack);
        }
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return false;
    }

    @Override
    public void clearContent() {
        for (TagItemList list : both) {
            for (int i = 0; i < list.get().length; i++) {
                list.get()[0] = null;
            }
        }
    }
}
