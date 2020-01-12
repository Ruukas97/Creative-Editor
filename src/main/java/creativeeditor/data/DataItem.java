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
		super();
	}

	public DataItem(ItemStack stack) {
		super();
		setItemStack(stack);
	}

	public DataItem(byte count, byte slot, Item item, CompoundNBT tag) {
		this();
		setCount(count);
		setSlot(slot);
		setItem(item);
		setItemNBT(tag);
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
		return (TagItemID) getDataDefaulted("id", new TagItemID());
	}

	public Item getItem() {
		return getItemTag().getItem();
	}

	public void setItem(Item item) {
		getItemTag().setItem(item);
	}

	public NumberRange getCountTag() {
		return (NumberRange) getDataDefaulted("Count", new NumberRange(1, 64));
	}

	public byte getCount() {
		return getCountTag().getNumber().byteValue();
	}

	public void setCount(Number count) {
		getCountTag().setNumber(count);
	}

	public NumberRange getSlotTag() {
		return (NumberRange) getDataDefaulted("Slot", new NumberRange(1, 45));
	}

	public byte getSlot() {
		return getSlotTag().getNumber().byteValue();
	}

	public void setSlot(Number slot) {
		getSlotTag().setNumber(slot.byteValue());
	}

	public TagItemNBT getItemNBTTag() {
		return (TagItemNBT) getDataDefaulted("tag", new TagItemNBT());
	}

	public CompoundNBT getItemNBT() {
		CompoundNBT nbt = (CompoundNBT) getItemNBTTag().getNBT();
		return nbt;
	}

	public void setItemNBT(CompoundNBT nbt) {
		put("tag", new TagItemNBT(nbt));
	}
}
