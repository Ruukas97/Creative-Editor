package infinityitemeditor.data.base;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.NumberRangeInt;
import lombok.Getter;
import net.minecraft.core.Rotations;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;

public class DataRotation implements Data<Rotations, ListTag> {
    private @Getter
    final
    NumberRangeInt x;
    private @Getter
    final
    NumberRangeInt y;
    private @Getter
    final
    NumberRangeInt z;


    public DataRotation(ListTag nbt) {
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
    public ListTag getTag() {
        ListTag nbt = new ListTag();
        nbt.add(FloatTag.valueOf(x.get()));
        nbt.add(FloatTag.valueOf(y.get()));
        nbt.add(FloatTag.valueOf(z.get()));
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        IFormattableTextComponent x = new TextComponent("X").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE).append(":").append(String.valueOf(this.x.get())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//        IFormattableTextComponent y = new TextComponent("Y").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE).append(":").append(String.valueOf(this.y.get())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//        IFormattableTextComponent z = new TextComponent("Z").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE).append(":").append(String.valueOf(this.z.get())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//        return x.append(", ").append(y).append(", ").append(z);
//    }


    @Override
    public Rotations getData() {
        return new Rotations(x.get(), y.get(), z.get());
    }
}
