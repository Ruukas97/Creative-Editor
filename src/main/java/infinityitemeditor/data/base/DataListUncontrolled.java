package infinityitemeditor.data.base;

import infinityitemeditor.data.Data;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;

public class DataListUncontrolled extends DataList<Data<?, ?>> {
    public DataListUncontrolled() {
        super();
    }


    public DataListUncontrolled(ListTag nbt) {
        this();
        nbt.forEach(this::add);
    }


    public DataListUncontrolled(List<Data<?, ?>> list) {
        super(list);
    }


    @Override
    public <T extends Tag> void add(T nbt) {
        Data<?, ?> res = Data.getDataFromNBT(nbt);
        if (res != null)
            add(res);
    }
}
