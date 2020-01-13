package creativeeditor.data;

import javax.annotation.Nullable;

import creativeeditor.data.base.DataMap;
import creativeeditor.data.tag.TagItemID;
import creativeeditor.data.tag.TagItemNBT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DataItem extends DataMap {
	// Wiki: https://minecraft.gamepedia.com/Player.dat_format#Item_structure

	public DataItem() {
		this(ItemStack.EMPTY);
	}

	public DataItem(ItemStack stack) {
		setItemStack(stack);
	}

	public DataItem(byte count, byte slot, Item item, CompoundNBT tag) {
		this();
		setCount(count);
		setSlot(slot);
		setItem(item);
		setItemNBT(tag);
	}

	public DataItem(CompoundNBT nbt) {
		this(ItemStack.read(nbt));
	}

	public ItemStack getItemStack() {
		return ItemStack.read((CompoundNBT) getNBT());
	}

	public void setItemStack(ItemStack stack) {
		setCount(stack.getCount());
		setItem(stack.getItem());
		setItemNBT(stack.getTag());
	}

	@Nullable
	public TagItemID getItemTag() {
		return (TagItemID) getDataDefaultedForced("id", new TagItemID());
	}

	public Item getItem() {
		return getItemTag().getItem();
	}

	public void setItem(Item item) {
		getItemTag().setItem(item);
	}

	public NumberRange getCountTag() {
		return (NumberRange) getDataDefaultedForced("Count", new NumberRange(1, 64));
	}

	public int getCount() {
		return getCountTag().get();
	}

	public void setCount(int count) {
		getCountTag().set(count);
	}

	public NumberRange getSlotTag() {
		return (NumberRange) getDataDefaultedForced("Slot", new NumberRange(1, 45));
	}

	public int getSlot() {
		return getSlotTag().get();
	}

	public void setSlot(int slot) {
		getSlotTag().set(slot);
	}

	public TagItemNBT getItemNBTTag() {
		return (TagItemNBT) getDataDefaultedForced("tag", new TagItemNBT());
	}

	public CompoundNBT getItemNBT() {
		return getItemNBTTag().getNBT();
	}

	public void setItemNBT(CompoundNBT nbt) {
		put("tag", new TagItemNBT(nbt));
	}
}
