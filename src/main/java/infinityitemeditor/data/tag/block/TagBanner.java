package infinityitemeditor.data.tag.block;

import infinityitemeditor.data.tag.TagBannerPattern;
import infinityitemeditor.data.tag.TagList;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class TagBanner extends TagTileEntity<BannerTileEntity> {
    @Getter
    private final TagList<TagBannerPattern> patterns;


    public TagBanner(CompoundNBT nbt) {
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
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(NBTKeys.keys.tagPatterns(), patterns.getNBT());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return getNBT().getPrettyDisplay(space, indentation);
    }
}
