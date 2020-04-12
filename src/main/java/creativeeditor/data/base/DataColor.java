package creativeeditor.data.base;

import creativeeditor.util.ColorUtils.Color;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.IntNBT;

public class DataColor extends SingularData<Color, IntNBT> {
    @Getter
    @Setter
    private int defColor = 0;
    
    public DataColor(Color color) {
        super( color );
    }


    public DataColor(int color) {
        this( new Color( color ) );
    }

    @Override
    public boolean isDefault() {
        return data.getInt() == defColor;
    }


    @Override
    public IntNBT getNBT() {
        return new IntNBT( data.getInt() );
    }
    
    public int getInt() {
        return data.getInt();
    }
}
