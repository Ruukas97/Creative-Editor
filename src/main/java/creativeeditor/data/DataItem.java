package creativeeditor.data;

import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import creativeeditor.data.base.DataMap;
import creativeeditor.data.tag.TagDamage;
import creativeeditor.data.tag.TagDisplayName;
import creativeeditor.data.tag.TagItemID;
import creativeeditor.data.tag.TagItemNBT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DataItem extends DataMap {
	private TagItemID item;
	private NumberRange count, slot;
	private TagItemNBT tag;

	public DataItem(TagItemID item, NumberRange count, TagItemNBT tag, NumberRange slot,
			Map<String, Data<?, ?>> extra) {
		super(extra);
		this.item = item;
		this.count = count;
		this.slot = slot;
		this.tag = tag;
	}

	public DataItem() {
		this(ItemStack.EMPTY, 0);
	}

	public DataItem(ItemStack stack) {
		this(stack, 0);
	}

	public DataItem(ItemStack stack, int slot) {
		this(stack.getItem(), stack.getCount(), stack.getTag(), slot);
	}

	public DataItem(Item item, int count, CompoundNBT tag, int slot) {
		this(new TagItemID(item), new NumberRange(count, 1, 64), new TagItemNBT(tag), new NumberRange(slot, 0, 45),
				Maps.newHashMap());
	}

	/**
	 * This reads the map into an ItemStack including all keys. So no default checks
	 * are made. This should be used mainly when the itemstack is needed in an
	 * isDefault check to avoid creating endless loops.
	 * 
	 * @return An itemstack including all data, with no cleanup.
	 */
	public ItemStack getItemStack() {
		return ItemStack.read(getNBTIncludeAll());
	}

	public ItemStack getItemStackClean() {
		return ItemStack.read(getNBT());
	}

	public void setFromItemStack(ItemStack stack) {
		item.setItem(stack.getItem());
		count.set(stack.getCount());
		tag = new TagItemNBT(stack.getTag());
	}

	@Nonnull
	public TagItemID getItemTag() {
		return this.getItemTag();
	}

	@Nonnull
	public NumberRange getCountTag() {
		return count;
	}

	public NumberRange getSlotTag() {
		return slot;
	}

	public TagItemNBT getItemNBTTag() {
		return tag;
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
		return new DataItem(item.copy(), count.copy(), tag.copy(), slot.copy(), getMapCopy());
	}

	@Override
	public CompoundNBT getNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.put("id", item.getNBT());
		nbt.put("Count", count.getNBT());
		nbt.put("tag", tag.getNBT());
		data.forEach((key, value) -> {
			if (!value.isDefault())
				nbt.put(key, value.getNBT());
		});
		return nbt;
	}
}
