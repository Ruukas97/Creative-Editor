package infinityitemeditor.data.tag;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.base.DataColor;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.render.NBTIcons;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.item.FireworkRocketItem.Shape;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class TagExplosion implements Data<TagExplosion, CompoundNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;

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

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        if (isDefault() || shape == null) {
            return new StringTextComponent("{}");
        } else {
            return new StringTextComponent(shape.getName()).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        }
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.COMPOUND_TAG.renderIcon(mc, matrix, x, y);
    }
}
