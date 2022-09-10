package infinityitemeditor.data.tag;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataDouble;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.render.NBTIcons;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapDecoration;

public class TagMapDecoration implements Data<TagMapDecoration, CompoundNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;

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

    public TagMapDecoration(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }

    public TagMapDecoration(CompoundNBT nbt) {
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
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        NBTKeys keys = NBTKeys.keys;
        nbt.put(keys.decorationId(), id.getNBT());
        nbt.put(keys.decorationType(), ByteNBT.valueOf(type.getIcon()));
        nbt.put("x", x.getNBT());
        nbt.put("y", y.getNBT());
        nbt.put(keys.decorationRotation(), rotation.getNBT());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return getNBT().getPrettyDisplay(space, indentation);
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.COMPOUND_TAG.renderIcon(mc, matrix, x, y);
    }
}
