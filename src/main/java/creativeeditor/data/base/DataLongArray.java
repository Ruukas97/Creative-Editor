package creativeeditor.data.base;

import net.minecraft.nbt.LongArrayNBT;

public class DataLongArray extends SingularData<long[], LongArrayNBT> {

    public DataLongArray(long... values) {
        super(values);
    }


    public DataLongArray(LongArrayNBT nbt) {
        this(nbt.getAsLongArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public LongArrayNBT getNBT() {
        return new LongArrayNBT(data);
    }
}
