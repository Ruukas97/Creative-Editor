package creativeeditor.data.tag;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataList;
import creativeeditor.data.base.DataMap;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
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

	private @Getter @Setter TagGameProfile skullOwner;

	public TagItemNBT(DataItem item, CompoundNBT nbt) {
		super();
		nbt = nbt != null ? nbt.copy() : new CompoundNBT();
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

		if (nbt.contains(keys.tagSkullOwner(), NBT.TAG_COMPOUND)) {
			skullOwner = new TagGameProfile(nbt.getCompound(keys.tagSkullOwner()));
		} else if (nbt.contains(keys.tagSkullOwner(), NBT.TAG_STRING)
				&& !StringUtils.isBlank(nbt.getString(keys.tagSkullOwner()))) {
			skullOwner = new TagGameProfile(nbt.getString(keys.tagSkullOwner()));
		} else {
			skullOwner = new TagGameProfile((GameProfile)null);
		}
	}

	@Override
	public CompoundNBT getNBT() {
		NBTKeys keys = NBTKeys.keys;
		CompoundNBT nbt = new CompoundNBT();
		if(!damage.isDefault())
			nbt.put(keys.tagDamage(), damage.getNBT());
		if(!unbreakable.isDefault())
			nbt.put(keys.tagUnbreakable(), unbreakable.getNBT());
		if(!canDestroy.isDefault())
			nbt.put(keys.tagCanDestroy(), canDestroy.getNBT());
		if(!canPlaceOn.isDefault())
			nbt.put(keys.tagCanPlaceOn(), canPlaceOn.getNBT());
		if(!display.isDefault())
			nbt.put(keys.tagDisplay(), display.getNBT());
		if(!skullOwner.isDefault())
			nbt.put(keys.tagSkullOwner(), skullOwner.getNBT());
		return nbt;
	}

	@Override
	public boolean isDefault() {
		return super.isDefault();
	}
}
