package creative.editor.nbt;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class NBTItemBase extends EditNBT {
	// Wiki: https://minecraft.gamepedia.com/Player.dat_format#Item_structure
	protected byte count;
	protected byte slot = 0;
	protected Item item; // Should be translated to "id" String, if making an Item tag
	protected CompoundNBT tag;

	public NBTItemBase() {
		this(ItemStack.EMPTY);
	}

	public NBTItemBase(ItemStack stack) {
		setItemStack(stack);
	}

	public NBTItemBase(byte count, byte slot, Item item, CompoundNBT tag) {
		this.count = count;
		this.slot = slot;
		this.item = item;
		this.tag = tag;
	}

	public void setItemStack(ItemStack stack) {
		this.count = (byte) stack.getCount();
		this.item = stack.getItem();
		this.tag = stack.getTag();
	}

	public ItemStack getItemStack() {
		return new ItemStack(item, count, tag);
	}

	public byte setSlot(byte slot) {
		return (this.slot = slot);
	}

	public byte getSlot() {
		return slot;
	}

	@Override
	public INBT getNBT() {
		CompoundNBT root = new CompoundNBT();
		root.putByte("Count", count);
		root.putByte("Slot", slot);
		root.putString("id", item.getRegistryName().getPath());
		root.put("tag", tag);
		return root;
	}
}
