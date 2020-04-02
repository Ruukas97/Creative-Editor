package creativeeditor.data.base;

import net.minecraft.nbt.IntArrayNBT;

public class DataIntegerArray extends SingularData<int[], IntArrayNBT> {
    public DataIntegerArray(int... values) {
        super( values );
    }


    public DataIntegerArray(IntArrayNBT nbt) {
        this( nbt.getIntArray() );
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public IntArrayNBT getNBT() {
        return new IntArrayNBT( data );
    }
}
