package infinityitemeditor.data.base;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.List;

public class DataListString extends DataList<DataString> {
    public DataListString() {
        super();
    }


    public DataListString(ListNBT nbt) {
        super();
        nbt.forEach(this::add);
    }


    public DataListString(List<DataString> list) {
        super(list);
    }


    @Override
    public <T extends INBT> void add(T nbt) {
        if (nbt != null && nbt.getId() == NBT.TAG_STRING) {
            StringNBT str = (StringNBT) nbt;
            if (str != null && !str.getAsString().isEmpty()) {
                add(new DataString(str));
            }
        }
    }
}
