package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.NumberRangeInt;
import lombok.Getter;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Rotations;

public class DataRotation implements Data<Rotations, ListNBT> {
    private @Getter
    final
    NumberRangeInt x;
    private @Getter
    final
    NumberRangeInt y;
    private @Getter
    final
    NumberRangeInt z;


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
    public Rotations getData() {
        return new Rotations(x.get(), y.get(), z.get());
    }
}
