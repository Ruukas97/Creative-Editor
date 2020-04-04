package creativeeditor.data.base;

import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.Rotations;

public class DataRotation extends SingularData<Rotations, ListNBT> {
    
    public DataRotation(ListNBT data) {
        this( new Rotations( data ) );
    }

    public DataRotation(Rotations data) {
        super( data );
    }

    @Override
    public boolean isDefault() {
        return data.getX() + data.getY() + data.getZ() == 0;
    }


    @Override
    public ListNBT getNBT() {
        return data.writeToNBT();
    }
}
