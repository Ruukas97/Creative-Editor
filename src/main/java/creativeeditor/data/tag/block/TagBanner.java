package creativeeditor.data.tag.block;

import creativeeditor.data.tag.TagBannerPattern;
import creativeeditor.data.tag.TagList;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TagBanner extends TagTileEntity<BannerTileEntity> {
    @Getter
    private TagList<TagBannerPattern> patterns;


    public TagBanner(CompoundNBT nbt) {
        patterns = new TagList<>( nbt.getList( NBTKeys.keys.tagPatterns(), NBT.TAG_COMPOUND ), TagBannerPattern::new );
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
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put( NBTKeys.keys.tagPatterns(), patterns.getNBT() );
        return nbt;
    }
}
