package creativeeditor.data;

import java.util.Map;

import javax.annotation.Nullable;

import creativeeditor.data.base.DataMap;
import creativeeditor.data.tag.TagDamage;
import creativeeditor.data.tag.TagDisplayName;
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
		super();
		setItemStack(stack);
	}

	public DataItem(byte count, byte slot, Item item, CompoundNBT tag) {
		super();
		setItem(item);
		setCount(count);
		setSlot(slot);
		setItemNBT(tag);
	}

	public DataItem(CompoundNBT nbt) {
		super(nbt);
	}

	public DataItem(Map<String, Data<?, ?>> map) {
		super(map);
	}
	
	/**
	 * This reads the map into an ItemStack including all keys. So no default checks are made.
	 * This should be used mainly when the itemstack is needed in an isDefault check to avoid creating endless loops.
	 * @return An itemstack including all data, with no cleanup.
	 */
	public ItemStack getItemStack() {
		return ItemStack.read(getNBTIncludeAll());
	}
	
	public ItemStack getItemStackClean() {
		return ItemStack.read(getNBT());
	}

	public void setItemStack(ItemStack stack) {
		setItem(stack.getItem());
		setCount(stack.getCount());
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

	public TagDisplayName getDisplayNameTag() {
		return getItemNBTTag().getDisplayTag().getNameTag(this);
	}

	public void clearCustomName() {
		getItemNBTTag().getDisplayTag().get().remove("Name");
	}

	public TagDamage getDamageTag() {
		return getItemNBTTag().getDamageTag(this);
	}

	@Override
	public DataItem copy() {
		return new DataItem(getMapCopy());
	}
}
