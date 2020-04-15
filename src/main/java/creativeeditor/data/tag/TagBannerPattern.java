package creativeeditor.data.tag;

import org.apache.commons.lang3.tuple.Pair;

import creativeeditor.data.Data;
import creativeeditor.data.version.NBTKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.BannerPattern;

@AllArgsConstructor
public class TagBannerPattern implements Data<Pair<BannerPattern, DyeColor>, CompoundNBT> {
    @Getter
    @Setter
    private BannerPattern pattern;
    @Getter
    @Setter
    private DyeColor color;


    public TagBannerPattern(INBT nbt) {
        this( nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT() );
    }


    public TagBannerPattern(CompoundNBT nbt) {
        NBTKeys keys = NBTKeys.keys;
        color = DyeColor.byId( nbt.getInt( keys.patternColor() ) );
        pattern = BannerPattern.byHash( nbt.getString( keys.patternPattern() ) );
    }


    @Override
    public Pair<BannerPattern, DyeColor> getData() {
        return Pair.of( pattern, color );
    }


    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt( keys.patternColor(), color.getId() );
        nbt.putInt( keys.patternColor(), -1 );
        nbt.putString( keys.patternPattern(), pattern.getHashname() );
        return nbt;
    }
}
