package creativeeditor.data.base;

import net.minecraft.nbt.IntNBT;

public class DataBitField extends SingularData<boolean[], IntNBT> {
    private boolean keepSize;


    public DataBitField(boolean keepSize, boolean... data) {
        super( data );
        this.keepSize = keepSize;
    }


    public DataBitField(int size, IntNBT data) {
        this( size, data.getInt() );
    }
    
    public DataBitField(int size, int data) {
        super( new boolean[size] );
        this.keepSize = true;
        setInt( data );
    }


    @Override
    public void set( boolean[] value ) {
        if (keepSize && value.length != data.length) {
            for (int i = 0; i < value.length && i < data.length; i++) {
                data[i] = value[i];
            }
        }
        else
            super.set( value );
    }


    @Override
    public boolean isDefault() {
        return getInt() == 0;
    }


    public int getInt() {
        return booleanArrayToInt( data );
    }


    public void setInt( int value ) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (value & (1 << i)) != 0;
        }
    }


    public static int booleanArrayToInt( boolean[] array ) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                result += 1 << i;
            }
        }
        return result;
    }


    @Override
    public IntNBT getNBT() {
        return new IntNBT( getInt() );
    }
}
