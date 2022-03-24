package infinityitemeditor.data.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.render.NBTIcons;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataRotation implements Data<Rotations, ListNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;
    @Getter
    private final NumberRangeInt x;
    @Getter
    private final NumberRangeInt y;
    @Getter
    private final NumberRangeInt z;


    public DataRotation(ListNBT nbt) {
        x = new NumberRangeInt((int) nbt.getFloat(0), 0, 360);
        y = new NumberRangeInt((int) nbt.getFloat(1), 0, 360);
        z = new NumberRangeInt((int) nbt.getFloat(2), 0, 360);
    }


    public DataRotation(Rotations rot) {
        x = new NumberRangeInt((int) rot.getX(), 0, 360);
        y = new NumberRangeInt((int) rot.getY(), 0, 360);
        z = new NumberRangeInt((int) rot.getZ(), 0, 360);
    }


    @Override
    public boolean isDefault() {
        return getData().equals(new Rotations(0f, 0f, 0f));
    }


    @Override
    public ListNBT getNBT() {
        ListNBT nbt = new ListNBT();
        nbt.add(FloatNBT.valueOf(x.get()));
        nbt.add(FloatNBT.valueOf(y.get()));
        nbt.add(FloatNBT.valueOf(z.get()));
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        IFormattableTextComponent x = new StringTextComponent("X").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE).append(":").append(String.valueOf(this.x.get())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
        IFormattableTextComponent y = new StringTextComponent("Y").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE).append(":").append(String.valueOf(this.y.get())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
        IFormattableTextComponent z = new StringTextComponent("Z").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE).append(":").append(String.valueOf(this.z.get())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
        return x.append(", ").append(y).append(", ").append(z);
    }


    @Override
    public Rotations getData() {
        return new Rotations(x.get(), y.get(), z.get());
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.LIST.renderIcon(mc, matrix, x, y);
    }
}
