package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataDouble;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.maps.MapDecoration;

public class TagMapDecoration implements Data<TagMapDecoration, CompoundTag> {

    @Getter
    private final DataString id;
    @Getter
    @Setter
    private MapDecoration.Type type;
    @Getter
    private final DataDouble x;
    @Getter
    private final DataDouble y;
    @Getter
    private final DataDouble rotation;

    public TagMapDecoration(Tag nbt) {
        this(nbt instanceof CompoundTag ? (CompoundTag) nbt : new CompoundTag());
    }

    public TagMapDecoration(CompoundTag nbt) {
        NBTKeys keys = NBTKeys.keys;
        id = new DataString(nbt.getString(keys.decorationId()));
        type = MapDecoration.Type.byIcon(nbt.getByte(keys.decorationType()));
        x = new DataDouble(nbt.getDouble("x"));
        y = new DataDouble(nbt.getDouble("y"));
        rotation = new DataDouble(nbt.getDouble(keys.decorationRotation()));
    }


    @Override
    public TagMapDecoration getData() {
        return null;
    }


    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public CompoundTag getTag() {
        CompoundTag nbt = new CompoundTag();
        NBTKeys keys = NBTKeys.keys;
        nbt.put(keys.decorationId(), id.getTag());
        nbt.put(keys.decorationType(), ByteTag.valueOf(type.getIcon()));
        nbt.put("x", x.getTag());
        nbt.put("y", y.getTag());
        nbt.put(keys.decorationRotation(), rotation.getTag());
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return getTag().getPrettyDisplay(space, indentation);
//    }
}
