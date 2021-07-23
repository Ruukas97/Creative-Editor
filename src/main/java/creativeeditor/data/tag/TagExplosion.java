package creativeeditor.data.tag;

import creativeeditor.data.Data;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataColor;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.item.FireworkRocketItem.Shape;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagExplosion implements Data<TagExplosion, CompoundNBT> {
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


    public TagExplosion(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }


    public TagExplosion(CompoundNBT nbt) {
        NBTKeys keys = NBTKeys.keys;
        flicker = new DataBoolean(nbt.getBoolean(keys.explosionFlicker()));
        trail = new DataBoolean(nbt.getBoolean(keys.explosionTrail()));
        shape = Shape.byId(nbt.getByte(keys.explosionShape()));
        colors = new TagList<>(nbt.getList(keys.explosionColors(), NBT.TAG_INT), DataColor::new);
        fadeColors = new TagList<>(nbt.getList(keys.explosionFadeColor(), NBT.TAG_INT), DataColor::new);
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
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        if (!flicker.isDefault())
            nbt.put(keys.explosionFlicker(), flicker.getNBT());
        if (!trail.isDefault())
            nbt.put(keys.explosionTrail(), trail.getNBT());
        if (shape != null)
            nbt.put(keys.explosionShape(), ByteNBT.valueOf((byte) shape.ordinal()));
        if (!colors.isDefault())
            nbt.put(keys.explosionColors(), colors.getNBT());
        if (!getFadeColors().isDefault())
            nbt.put(keys.explosionFadeColor(), fadeColors.getNBT());
        return nbt;
    }
}
