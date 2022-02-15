package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.version.NBTKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.apache.commons.lang3.tuple.Pair;

@AllArgsConstructor
public class TagBannerPattern implements Data<Pair<BannerPattern, DyeColor>, CompoundTag> {
    @Getter
    @Setter
    private BannerPattern pattern;
    @Getter
    @Setter
    private DyeColor color;


    public TagBannerPattern(Tag nbt) {
        this(nbt instanceof CompoundTag ? (CompoundTag) nbt : new CompoundTag());
    }


    public TagBannerPattern(CompoundTag nbt) {
        NBTKeys keys = NBTKeys.keys;
        color = DyeColor.byId(nbt.getInt(keys.patternColor()));
        pattern = BannerPattern.byHash(nbt.getString(keys.patternPattern()));
    }


    @Override
    public Pair<BannerPattern, DyeColor> getData() {
        return Pair.of(pattern, color);
    }


    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public CompoundTag getTag() {
        NBTKeys keys = NBTKeys.keys;
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(keys.patternColor(), color.getId());
        nbt.putString(keys.patternPattern(), pattern.getHashname());
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return new TextComponent(color.getName()).append(" ").append(pattern.getFilename());
//    }
}
