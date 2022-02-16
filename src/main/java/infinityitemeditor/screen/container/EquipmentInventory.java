package infinityitemeditor.screen.container;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagItemList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

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
        return armor.getData().size() + hands.get().size();
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
        List<DataItem> list = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().size()) {
                list = itemList.get();
                break;
            }

            index -= itemList.get().size();
        }

        return list == null || list.get(index) == null ? ItemStack.EMPTY : list.get(index).getItemStack();
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        List<DataItem> list = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().size()) {
                list = itemList.get();
                break;
            }

            index -= itemList.get().size();
        }
        if (list != null && list.get(index) != null && !list.get(index).getItemStack().isEmpty()) {
            DataItem old = list.get(index);
            ItemStack is = removeItem(list, index, count);
            int i = old.getCount().get() - is.getCount();
            list.set(index, i > 0 ? new DataItem(is.getItem(), i, old.getItemStack().getTag(), 0) : new DataItem());
            return is;
        }
        return ItemStack.EMPTY;
    }


    private static ItemStack removeItem(List<DataItem> list, int index, int count) {
        return index >= 0 && index < list.size() && !list.get(index).getItemStack().isEmpty() && count > 0 ? list.get(index).split(count).getItemStack() : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        List<DataItem> list = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().size()) {
                list = itemList.get();
                break;
            }

            index -= itemList.get().size();
        }

        if (list != null && list.get(index) != null && !list.get(index).getItemStack().isEmpty()) {
            DataItem item = list.get(index);
            list.set(index, new DataItem());
            return item.getItemStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        List<DataItem> list = null;

        for (TagItemList itemList : both) {
            if (index < itemList.get().size()) {
                list = itemList.get();
                break;
            }

            index -= itemList.get().size();
        }

        if (list != null) {
            list.set(index, new DataItem(stack));
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
            for (int i = 0; i < list.get().size(); i++) {
                list.get().set(0, null);
            }
        }
    }
}
