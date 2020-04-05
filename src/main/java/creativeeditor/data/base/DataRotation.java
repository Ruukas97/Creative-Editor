package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.NumberRangeInt;
import lombok.Getter;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Rotations;

public class DataRotation implements Data<Rotations, ListNBT> {
    private @Getter NumberRangeInt x;
    private @Getter NumberRangeInt y;
    private @Getter NumberRangeInt z;


    public DataRotation(ListNBT nbt) {
        x = new NumberRangeInt( nbt.getInt( 0 ), 0, 360 );
        y = new NumberRangeInt( nbt.getInt( 1 ), 0, 360 );
        z = new NumberRangeInt( nbt.getInt( 2 ), 0, 360 );
    }


    public DataRotation(Rotations rot) {
        x = new NumberRangeInt( (int) rot.getX(), 0, 360 );
        y = new NumberRangeInt( (int) rot.getY(), 0, 360 );
        z = new NumberRangeInt( (int) rot.getZ(), 0, 360 );
    }


    @Override
    public boolean isDefault() {
        return getData().equals( new Rotations( 0f, 0f, 0f ) );
    }


    @Override
    public ListNBT getNBT() {
        ListNBT nbt = new ListNBT();
        nbt.add( new FloatNBT( x.get() ) );
        nbt.add( new FloatNBT( y.get() ) );
        nbt.add( new FloatNBT( z.get() ) );
        return nbt;
    }


    @Override
    public Rotations getData() {
        return new Rotations( x.get(), y.get(), z.get() );
    }
}
