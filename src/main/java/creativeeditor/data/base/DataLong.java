package creativeeditor.data.base;

import net.minecraft.nbt.LongNBT;

public class DataLong extends SingularData<Long, LongNBT> {

    public DataLong() {
        this( 0 );
    }


    public DataLong(long value) {
        super( value );
    }


    public DataLong(LongNBT nbt) {
        this( nbt.getLong() );
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public LongNBT getNBT() {
        return LongNBT.valueOf( data );
    }
}
