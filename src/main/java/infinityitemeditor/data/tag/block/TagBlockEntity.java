package infinityitemeditor.data.tag.block;

import infinityitemeditor.data.DataUnserializedCompound;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.data.tag.TagBannerPattern;
import infinityitemeditor.data.tag.TagItemList;
import infinityitemeditor.data.tag.TagList;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

public class TagBlockEntity extends DataUnserializedCompound {
    @Getter
    private final DataString customName;
    @Getter
    private final DataString locked;
    @Getter
    private final TagItemList items;

    // Banner
    @Getter
    private final TagList<TagBannerPattern> patterns;

    public TagBlockEntity(CompoundNBT nbt) {
        super(nbt);
        if(nbt == null){
            nbt = new CompoundNBT();
        }
        NBTKeys keys = NBTKeys.keys;
        customName = add(keys.blockEntityCustomName(), new DataString(nbt.getString(keys.blockEntityCustomName())));
        locked = add(keys.locked(), new DataString(nbt.getString(keys.locked())));
        items = add(keys.blockEntityItems(), new TagItemList(nbt.getList(keys.blockEntityItems(), Constants.NBT.TAG_COMPOUND)));

        patterns = add(keys.tagPatterns(), new TagList<>(nbt.getList(keys.tagPatterns(), Constants.NBT.TAG_COMPOUND), TagBannerPattern::new));
    }

    @Override
    public TagBlockEntity getData() {
        return this;
    }
}
