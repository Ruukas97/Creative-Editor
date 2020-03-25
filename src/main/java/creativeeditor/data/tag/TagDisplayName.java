package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataTextComponent;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TagDisplayName extends DataTextComponent {
	private @Getter DataItem item;
	
	public TagDisplayName(DataItem item) {
		super(new StringTextComponent(item.getItemStack().getDisplayName().getFormattedText()));
		this.item = item;
	}

	@Override
	public boolean isDefault() {
		if(getUnformatted().length() == 0) {
			return true;
		}

		return item.getItemStack().getDisplayName().getFormattedText().equals(getDefault().getFormattedText());
	}

	public void reset() {
		set(getDefault());
	}
	
	public ITextComponent getDefault() {
		NBTKeys keys = NBTKeys.keys;
		ItemStack copy = item.getItemStack();
		CompoundNBT display = copy.getChildTag(keys.tagDisplay());
		if(display != null) {
			display.remove(keys.displayName());
		}
		return copy.getDisplayName();
	}
}
