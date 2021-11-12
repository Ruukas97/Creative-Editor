package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataColor;
import infinityitemeditor.data.base.DataListString;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class TagDisplay implements Data<TagDisplay, CompoundNBT> {
    private final DataItem item;

    private final @Getter
    DataColor color;
    private final @Getter
    DataColor mapColor;
    private final @Getter
    TagDisplayName name;
    private final @Getter
    DataListString lore;


    public TagDisplay(DataItem item, CompoundNBT nbt) {
        this.item = item;
        NBTKeys keys = NBTKeys.keys;
        color = new DataColor(nbt.getInt(keys.displayColor()));
        mapColor = new DataColor(nbt.getInt(keys.displayMapColor()));
        name = new TagDisplayName(nbt.getString(keys.displayName()), this.item);
        lore = new DataListString(nbt.getList(keys.displayLore(), NBT.TAG_STRING));
    }


    @Override
    public boolean isDefault() {
        return color.isDefault() && mapColor.isDefault() && name.isDefault() && lore.isDefault();
    }


    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        if (!color.isDefault())
            nbt.put(keys.displayColor(), color.getNBT());
        if (!mapColor.isDefault())
            nbt.put(keys.displayMapColor(), mapColor.getNBT());
        if (!name.isDefault())
            nbt.put(keys.displayName(), name.getNBT());
        if (!lore.isDefault())
            nbt.put(keys.displayLore(), lore.getNBT());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return getNBT().getPrettyDisplay(space, indentation);
    }


    @Override
    public TagDisplay getData() {
        return this;
    }
}
