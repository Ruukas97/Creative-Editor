package creativeeditor.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class DataItem implements Data {
	// Wiki: https://minecraft.gamepedia.com/Player.dat_format#Item_structure
	protected DataRange count = new DataRange(0, 1, 64);
	protected byte slot;
	protected Item item; // Should be translated to "id" String, if making an Item tag
	protected CompoundNBT tag;

	public DataItem() {
		this(ItemStack.EMPTY);
	}

	public DataItem(ItemStack stack) {
		setItemStack(stack);
		slot = 0;
	}

	public DataItem(byte count, byte slot, Item item, CompoundNBT tag) {
		this.count.setNumber(count);
		this.slot = slot;
		this.item = item;
		this.tag = tag;
	}

	public void setItemStack(ItemStack stack) {
		this.count.setNumber(stack.getCount());
		this.item = stack.getItem();
		this.tag = stack.getTag();
	}

	public ItemStack getItemStack() {
		return new ItemStack(item, getCount(), tag);
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setCount(byte count) {
		this.count.setNumber(count);
	}

	public byte getCount() {
		return count.getNumber().byteValue();
	}

	public void setSlot(byte slot) {
		this.slot = slot;
	}

	public byte getSlot() {
		return slot;
	}

	public void setTag(CompoundNBT tag) {
		this.tag = tag;
	}

	public CompoundNBT getTag() {
		return tag;
	}

	@Override
	public INBT getNBT() {
		CompoundNBT root = new CompoundNBT();
		root.putByte("Count", getCount());
		root.putByte("Slot", slot);
		root.putString("id", item.getRegistryName().getPath());
		root.put("tag", tag);
		return root;
	}
}
