package creativeeditor.data.tag;

import creativeeditor.data.base.SingularData;
import net.minecraft.nbt.IntNBT;

public class TagDisplayHideFlags extends SingularData<TagDisplayHideFlags.HideFlags, IntNBT> {
	public TagDisplayHideFlags(HideFlags hideFlags) {
		super(hideFlags);
	}

	@Override
	public TagDisplayHideFlags copy() {
		return new TagDisplayHideFlags(new HideFlags(data.enchants, data.attributes, data.unbreakable, data.canDestroy,
				data.canPlaceOn, data.itemInfo));
	}

	public boolean getEnchantsHidden() {
		return data.enchants;
	}

	public void setEnchantsHidden(boolean value) {
		data.enchants = value;
	}

	public boolean getAttributesHidden() {
		return data.attributes;
	}

	public void setAttributesHidden(boolean value) {
		data.attributes = value;
	}

	public boolean getUnbreakableHidden() {
		return data.unbreakable;
	}

	public void setUnbreakableHidden(boolean value) {
		data.unbreakable = value;
	}

	public boolean getCanDestroyHidden() {
		return data.canDestroy;
	}

	public void setCanDestroyHidden(boolean value) {
		data.canDestroy = value;
	}

	public boolean getCanPlaceOnHidden() {
		return data.canPlaceOn;
	}

	public void setCanPlaceOnHidden(boolean value) {
		data.canPlaceOn = value;
	}

	public boolean getItemInfoHidden() {
		return data.itemInfo;
	}

	public void setItemInfoHidden(boolean value) {
		data.itemInfo = value;
	}

	public int getInt() {
		int result = 0;
		if (getEnchantsHidden())
			result += 1;
		if (getAttributesHidden())
			result += 2;
		if (getUnbreakableHidden())
			result += 4;
		if (getCanDestroyHidden())
			result += 8;
		if (getCanPlaceOnHidden())
			result += 16;
		if (getItemInfoHidden())
			result += 32;
		return result;
	}

	@Override
	public boolean isDefault() {
		return getInt() == 0;
	}

	@Override
	public IntNBT getNBT() {
		return new IntNBT(getInt());
	}

	public static class HideFlags {
		public boolean enchants, attributes, unbreakable, canDestroy, canPlaceOn, itemInfo;

		public HideFlags(boolean enchants, boolean attributes, boolean unbreakable, boolean canDestroy,
				boolean canPlaceOn, boolean itemInfo) {
			this.enchants = enchants;
			this.attributes = attributes;
			this.unbreakable = unbreakable;
			this.canDestroy = canDestroy;
			this.canPlaceOn = canPlaceOn;
			this.itemInfo = itemInfo;
		}

		public HideFlags() {
			this(false, false, false, false, false, false);
		}
	}
}
