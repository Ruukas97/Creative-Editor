package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataColor;
import infinityitemeditor.data.base.DataListString;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class TagDisplay implements Data<TagDisplay, CompoundTag> {
    private final DataItem item;

    private final @Getter
    DataColor color;
    private final @Getter
    DataColor mapColor;
    private final @Getter
    TagDisplayName name;
    private final @Getter
    DataListString lore;


    public TagDisplay(DataItem item, CompoundTag nbt) {
        this.item = item;
        NBTKeys keys = NBTKeys.keys;
        color = new DataColor(nbt.getInt(keys.displayColor()));
        mapColor = new DataColor(nbt.getInt(keys.displayMapColor()));
        name = new TagDisplayName(nbt.getString(keys.displayName()), this.item);
        lore = new DataListString(nbt.getList(keys.displayLore(), Tag.TAG_STRING));
    }


    @Override
    public boolean isDefault() {
        return color.isDefault() && mapColor.isDefault() && name.isDefault() && lore.isDefault();
    }


    @Override
    public CompoundTag getTag() {
        NBTKeys keys = NBTKeys.keys;
        CompoundTag nbt = new CompoundTag();
        if (!color.isDefault())
            nbt.put(keys.displayColor(), color.getTag());
        if (!mapColor.isDefault())
            nbt.put(keys.displayMapColor(), mapColor.getTag());
        if (!name.isDefault())
            nbt.put(keys.displayName(), name.getTag());
        if (!lore.isDefault())
            nbt.put(keys.displayLore(), lore.getTag());
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return getTag().getPrettyDisplay(space, indentation);
//    }


    @Override
    public TagDisplay getData() {
        return this;
    }
}
