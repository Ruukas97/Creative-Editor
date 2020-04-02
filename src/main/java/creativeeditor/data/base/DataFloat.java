package creativeeditor.data.base;

import net.minecraft.nbt.FloatNBT;

public class DataFloat extends SingularData<Float, FloatNBT> {

    public DataFloat(float value) {
        super( value );
    }


    public DataFloat(FloatNBT nbt) {
        this( nbt.getLong() );
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public FloatNBT getNBT() {
        return new FloatNBT( data );
    }
}
