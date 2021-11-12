package infinityitemeditor.data.base;

import infinityitemeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.List;

public class DataListUncontrolled extends DataList<Data<?, ?>> {
    public DataListUncontrolled() {
        super();
    }


    public DataListUncontrolled(ListNBT nbt) {
        this();
        nbt.forEach(this::add);
    }


    public DataListUncontrolled(List<Data<?, ?>> list) {
        super(list);
    }


    @Override
    public <T extends INBT> void add(T nbt) {
        Data<?, ?> res = Data.getDataFromNBT(nbt);
        if (res != null)
            add(res);
    }
}
