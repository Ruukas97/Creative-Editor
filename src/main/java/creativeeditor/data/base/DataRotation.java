package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.NumberRangeFloat;
import lombok.Getter;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Rotations;

public class DataRotation implements Data<Rotations, ListNBT> {
    private @Getter
    NumberRangeFloat x;
    private @Getter
    NumberRangeFloat y;
    private @Getter
    NumberRangeFloat z;


    public DataRotation(ListNBT nbt) {
        x = new NumberRangeFloat( nbt.getFloat( 0 ), 0f, 360f );
        y = new NumberRangeFloat( nbt.getFloat( 1 ), 0f, 360f );
        z = new NumberRangeFloat( nbt.getFloat( 2 ), 0f, 360f );
    }


    public DataRotation(Rotations rot) {
        x = new NumberRangeFloat( rot.getX(), 0f, 360f );
        y = new NumberRangeFloat( rot.getY(), 0f, 360f );
        z = new NumberRangeFloat( rot.getZ(), 0f, 360f );
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
