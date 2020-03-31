package creativeeditor.data.base;

import java.util.List;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class DataListUncontrolled extends DataList<Data<?, ?>> {
    public DataListUncontrolled() {
        super();
    }


    public DataListUncontrolled(ListNBT nbt) {
        this();
        nbt.forEach( this::add );
    }


    public DataListUncontrolled(List<Data<?, ?>> list) {
        super( list );
    }


    @Override
    public <T extends INBT> void add( T nbt ) {
        Data<?, ?> res = Data.getDataFromNBT( nbt );
        if (res != null)
            add( res );
    }
}
