package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataString;

public class TagDisplayName extends DataString {
	private DataItem item;
	public TagDisplayName(DataItem item) {
		super(item.getItemStack().getDisplayName().getFormattedText());
		this.item = item;
	}

	@Override
	public boolean isDefault() {
		return item.getItem().getDisplayName(item.getItemStack()).getFormattedText().equals(get());
	}
}
