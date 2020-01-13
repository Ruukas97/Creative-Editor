package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataTextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TagDisplayName extends DataTextComponent {
	private DataItem item;
	public TagDisplayName(DataItem item) {
		super(new StringTextComponent(item.getItemStack().getDisplayName().getFormattedText()));
		this.item = item;
	}

	@Override
	public boolean isDefault() {
		if(getUnformattedText().length() == 0) {
			return true;
		}
		DataItem copy = item.copy();
		copy.clearCustomName();
		return item.getItemStack().getDisplayName().getFormattedText().equals(copy.getItemStack().getDisplayName().getFormattedText());
	}
}
