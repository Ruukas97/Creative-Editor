package creativeeditor.data.tag;

import creativeeditor.data.base.DataBitField;

public class TagDisplayHideFlags extends DataBitField {
	public TagDisplayHideFlags(boolean enchants, boolean attributes, boolean unbreakable, boolean canDestroy, boolean canPlaceOn, boolean itemInfo) {
		super(true, enchants, attributes, unbreakable, canDestroy, canPlaceOn, itemInfo);
	}

	@Override
	public TagDisplayHideFlags copy() {
		return new TagDisplayHideFlags(getEnchantsHidden(), getAttributesHidden(), getUnbreakableHidden(), getCanDestroyHidden(), getCanPlaceOnHidden(), getItemInfoHidden());
	}
	
	public boolean getEnchantsHidden() {
		return data[0];
	}
	
	public void setEnchantsHidden(boolean value) {
		 data[0] = value;
	}
	
	public boolean getAttributesHidden() {
		return data[1];
	}
	
	public void setAttributesHidden(boolean value) {
		 data[1] = value;
	}
	
	public boolean getUnbreakableHidden() {
		return data[2];
	}
	
	public void setUnbreakableHidden(boolean value) {
		 data[2] = value;
	}
	
	public boolean getCanDestroyHidden() {
		return data[3];
	}
	
	public void setCanDestroyHidden(boolean value) {
		 data[3] = value;
	}
	
	public boolean getCanPlaceOnHidden() {
		return data[4];
	}
	
	public void setCanPlaceOnHidden(boolean value) {
		 data[4] = value;
	}
	
	public boolean getItemInfoHidden() {
		return data[5];
	}
	
	public void setItemInfoHidden(boolean value) {
		 data[5] = value;
	}
}
