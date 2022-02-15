package infinityitemeditor.data.tag.block;

import infinityitemeditor.data.tag.TagBannerPattern;
import infinityitemeditor.data.tag.TagList;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TagBanner extends TagTileEntity<BannerTileEntity> {
    @Getter
    private final TagList<TagBannerPattern> patterns;


    public TagBanner(CompoundTag nbt) {
        patterns = new TagList<>(nbt.getList(NBTKeys.keys.tagPatterns(), NBT.TAG_COMPOUND), TagBannerPattern::new);
    }


    @Override
    public BannerTileEntity getData() {
        return null;
    }


    @Override
    public boolean isDefault() {
        return patterns.isDefault();
    }


    @Override
    public CompoundTag getTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.put(NBTKeys.keys.tagPatterns(), patterns.getTag());
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return getTag().getPrettyDisplay(space, indentation);
//    }
}
