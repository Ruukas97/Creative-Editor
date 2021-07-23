package creativeeditor.data.base;

import net.minecraft.nbt.ByteNBT;

public class DataByte extends SingularData<Byte, ByteNBT> {
    public DataByte(byte value) {
        super(value);
    }


    public DataByte(ByteNBT nbt) {
        this(nbt.getAsByte());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public ByteNBT getNBT() {
        return ByteNBT.valueOf(data);
    }
}
