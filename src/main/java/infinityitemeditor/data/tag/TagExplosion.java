package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.base.DataColor;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.FireworkRocketItem.Shape;

public class TagExplosion implements Data<TagExplosion, CompoundTag> {
    @Getter
    private final DataBoolean flicker;
    @Getter
    private final DataBoolean trail;
    @Getter
    @Setter
    private Shape shape;
    @Getter
    private final TagList<DataColor> colors;
    @Getter
    private final TagList<DataColor> fadeColors;


    public TagExplosion(Tag nbt) {
        this(nbt instanceof CompoundTag ? (CompoundTag) nbt : new CompoundTag());
    }


    public TagExplosion(CompoundTag nbt) {
        NBTKeys keys = NBTKeys.keys;
        flicker = new DataBoolean(nbt.getBoolean(keys.explosionFlicker()));
        trail = new DataBoolean(nbt.getBoolean(keys.explosionTrail()));
        shape = Shape.byId(nbt.getByte(keys.explosionShape()));
        colors = new TagList<>(nbt.getList(keys.explosionColors(), Tag.TAG_INT), DataColor::new);
        fadeColors = new TagList<>(nbt.getList(keys.explosionFadeColor(), Tag.TAG_INT), DataColor::new);
    }


    @Override
    public TagExplosion getData() {
        return this;
    }


    @Override
    public boolean isDefault() {
        return flicker.isDefault() && trail.isDefault() && (shape == null || shape.ordinal() == 0) && colors.isDefault() && fadeColors.isDefault();
    }


    @Override
    public CompoundTag getTag() {
        NBTKeys keys = NBTKeys.keys;
        CompoundTag nbt = new CompoundTag();
        if (!flicker.isDefault())
            nbt.put(keys.explosionFlicker(), flicker.getTag());
        if (!trail.isDefault())
            nbt.put(keys.explosionTrail(), trail.getTag());
        if (shape != null)
            nbt.put(keys.explosionShape(), ByteTag.valueOf((byte) shape.ordinal()));
        if (!colors.isDefault())
            nbt.put(keys.explosionColors(), colors.getTag());
        if (!getFadeColors().isDefault())
            nbt.put(keys.explosionFadeColor(), fadeColors.getTag());
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        if (isDefault() || shape == null) {
//            return new TextComponent("{}");
//        } else {
//            return new TextComponent(shape.getName()).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        }
//    }
}
