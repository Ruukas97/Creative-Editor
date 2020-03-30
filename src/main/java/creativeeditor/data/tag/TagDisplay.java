package creativeeditor.data.tag;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataColor;
import creativeeditor.data.base.DataList;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagDisplay implements Data<TagDisplay, CompoundNBT> {
	private final DataItem item;

	private final @Getter DataColor color;
	private final @Getter TagDisplayName name;
	private final @Getter DataList lore;

	public TagDisplay(DataItem item, CompoundNBT nbt) {
		this.item = item;
		NBTKeys keys = NBTKeys.keys;
		CompoundNBT display = nbt.getCompound(keys.tagDisplay());
		color = new DataColor(display);
		name = new TagDisplayName(display.getString(keys.displayName()), this.item);
		lore = new DataList(display.getList(keys.displayLore(), NBT.TAG_STRING));
	}

	@Override
	public boolean isDefault() {
		return color.isDefault() && name.isDefault() && lore.isDefault();
	}

	@Override
	public CompoundNBT getNBT() {
		NBTKeys keys = NBTKeys.keys;
		CompoundNBT nbt = new CompoundNBT();
		if(!color.isDefault())
			nbt.put(keys.displayColor(), color.getNBT());
		if(!name.isDefault())
			nbt.put(keys.displayName(), name.getNBT());
		if(!lore.isDefault())
			nbt.put(keys.displayLore(), lore.getNBT());
		return nbt;
	}
}
