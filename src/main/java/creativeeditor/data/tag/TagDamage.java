package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRange;

public class TagDamage extends NumberRange {
	private DataItem item;

	public TagDamage(DataItem item) {
		super(0, 0);
		this.item = item;
	}
	
	@Override
	public NumberRange copy() {
		return new NumberRange(data, getMin(), getMax());
	}
	
	@Override
	public int getMax() {
		return item.getItemStack().getMaxDamage();
	}
}
