package creativeeditor.data.base;

import creativeeditor.util.ColorUtils.Color;
import net.minecraft.nbt.IntNBT;

public class DataColor extends SingularData<Color, IntNBT> {
    public DataColor(Color color) {
        super( color );
    }


    public DataColor(int color) {
        this( new Color( color ) );
    }

    @Override
    public boolean isDefault() {
        return data.getInt() == 0;
    }


    @Override
    public IntNBT getNBT() {
        return new IntNBT( data.getInt() );
    }
}
