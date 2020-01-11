package creativeeditor.data;

import javax.annotation.Nullable;

import creativeeditor.data.base.DataMap;
import creativeeditor.data.tag.TagItemID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class DataItem extends DataMap {
	// Wiki: https://minecraft.gamepedia.com/Player.dat_format#Item_structure

	public DataItem() {
		super(getKeyTags());
	}

	public DataItem(ItemStack stack) {
		super(getKeyTags());
		setItemStack(stack);
	}

	public DataItem(byte count, byte slot, Item item, CompoundNBT tag) {
		this();
		setCount(count);
		setSlot(slot);
		setItem(item);
		setItemNBT(tag);
	}

	public static KeyTag[] getKeyTags() {
		return new KeyTag[] { new DataRange(1, 1, 64).key("Count"), new DataRange(1, 1, 45).key("Slot"),
				new KeyTag("id", new TagItemID(Items.AIR)), new KeyTag("tag", new DataMap()) };
	}

	public ItemStack getItemStack() {
		return new ItemStack(getItem(), getCount(), getItemNBT());
	}

	public void setItemStack(ItemStack stack) {
		setCount(stack.getCount());
		setItem(stack.getItem());
		setItemNBT(stack.getTag());
	}
	
	@Nullable
	public TagItemID getItemTag() {
		return (TagItemID)getData("id");
	}

	public Item getItem() {
		return getItemTag().getItem();
	}

	public void setItem(Item item) {
		getItemTag().setItem(item);
	}
	
	public DataRange getCountTag() {
		return (DataRange)getData("Count");
	}

	public byte getCount() {
		return getCountTag().getNumber().byteValue();
	}

	public void setCount(Number count) {
		getCountTag().setNumber(count);
	}
	
	public DataRange getSlotTag() {
		return (DataRange)getData("Slot");
	}

	public byte getSlot() {
		return getSlotTag().getNumber().byteValue();
	}

	public void setSlot(Number slot) {
		getSlotTag().setNumber(slot.byteValue());
	}
	
	public DataMap getItemNBTTag() {
		return (DataMap)getData("tag");
	}
	
	public CompoundNBT getItemNBT() {
		return (CompoundNBT)getItemNBTTag().getNBT();
	}
	
	public void setItemNBT(CompoundNBT nbt) {
		put("tag", new DataMap(nbt));
	}
}
