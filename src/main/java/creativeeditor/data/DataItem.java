package creativeeditor.data;

import creativeeditor.data.tag.TagItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class DataItem implements Data {
	// Wiki: https://minecraft.gamepedia.com/Player.dat_format#Item_structure
	protected DataRange count = new DataRange(0, 1, 64);
	protected DataRange slot = new DataRange(1, 1, 45);
	protected TagItem item = new TagItem(Items.AIR);
	protected CompoundNBT tag;

	public DataItem() {
		this(ItemStack.EMPTY);
	}

	public DataItem(ItemStack stack) {
		setItemStack(stack);
	}

	public DataItem(byte count, byte slot, Item item, CompoundNBT tag) {
		setCount(count);
		setSlot(slot);
		setItem(item);
		this.tag = tag;
	}
	
	public ItemStack getItemStack() {
		return new ItemStack(getItem(), getCount(), tag);
	}

	public void setItemStack(ItemStack stack) {
		setCount(stack.getCount());
		setItem(stack.getItem());
		this.tag = stack.getTag();
	}
	
	public Item getItem() {
		return item.getItem();
	}

	public void setItem(Item item) {
		this.item.setItem(item);
	}
	
	public byte getCount() {
		return count.getNumber().byteValue();
	}

	public void setCount(Number count) {
		this.count.setNumber(count);
	}

	public byte getSlot() {
		return slot.getNumber().byteValue();
	}

	public void setSlot(Number slot) {
		this.slot.setNumber(slot.byteValue());
	}
	
	public CompoundNBT getTag() {
		return tag;
	}
	
	public void setTag(CompoundNBT tag) {
		this.tag = tag;
	}


	@Override
	public INBT getNBT() {
		CompoundNBT root = new CompoundNBT();
		root.put("Count", count.getNBT());
		root.put("Slot", slot.getNBT());
		root.put("id", item.getNBT());
		root.put("tag", tag);
		return root;
	}
}
