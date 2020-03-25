package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataList;
import creativeeditor.data.base.DataMap;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagItemNBT extends DataMap {
	private final @Getter DataItem item;

	// General
	private final @Getter TagDamage damage;
	private final @Getter DataBoolean unbreakable;
	private final @Getter DataList canDestroy;

	// Block Tags
	private final @Getter DataList canPlaceOn;

	// Display
	private final @Getter TagDisplay display;
	

	public TagItemNBT(DataItem item, CompoundNBT nbt) {
		super();
		nbt = nbt.copy();
		this.item = item;
		NBTKeys keys = NBTKeys.keys;
		// General
		damage = new TagDamage(this.item, nbt);
		unbreakable = new DataBoolean(nbt.getBoolean(keys.tagUnbreakable()));
		canDestroy = new DataList(nbt.getList(keys.tagCanDestroy(), NBT.TAG_STRING));

		// Block tags
		canPlaceOn = new DataList(nbt.getList(keys.tagCanPlaceOn(), NBT.TAG_STRING));

		// Display
		display = new TagDisplay(item, nbt);

	}

	@Override
	public CompoundNBT getNBT() {
		NBTKeys keys = NBTKeys.keys;
		CompoundNBT nbt = new CompoundNBT();
		nbt.put(keys.tagDamage(), damage.getNBT());
		nbt.put(keys.tagDisplay(), display.getNBT());
		return nbt;
	}

	@Override
	public boolean isDefault() {
		return super.isDefault();
	}
}
