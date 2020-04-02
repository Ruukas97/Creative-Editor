package creativeeditor.data.base;

import net.minecraft.nbt.IntNBT;

public class DataInteger extends SingularData<Integer, IntNBT> {
    public DataInteger() {
        this( 0 );
    }


    public DataInteger(IntNBT nbt) {
        this( nbt.getInt() );
    }


    public DataInteger(int value) {
        super( value );
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public IntNBT getNBT() {
        return new IntNBT( data );
    }
}
