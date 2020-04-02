package creativeeditor.data.base;

import net.minecraft.nbt.ByteArrayNBT;

public class DataByteArray extends SingularData<byte[], ByteArrayNBT> {
    public DataByteArray(byte... values) {
        super( values );
    }


    public DataByteArray(ByteArrayNBT nbt) {
        this( nbt.getByteArray() );
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public ByteArrayNBT getNBT() {
        return new ByteArrayNBT( data );
    }
}
