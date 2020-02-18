package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRange;
import creativeeditor.data.version.NBTKeys;
import net.minecraft.nbt.CompoundNBT;

public class TagDamage extends NumberRange {
	private DataItem item;

	public TagDamage(DataItem item) {
		super(0, 0);
		this.item = item;
	}
	
	public TagDamage(DataItem item, int value) {
		super(value, 0, 0);
		this.item = item;
	}
	
	public TagDamage(DataItem item, CompoundNBT parent) {
		super(parent.getInt(NBTKeys.keys.tagDamage()), 0, 0);
		this.item = item;
	}
	
	@Override
	public int getMax() {
		return item.getItemStack().getMaxDamage();
	}
}
