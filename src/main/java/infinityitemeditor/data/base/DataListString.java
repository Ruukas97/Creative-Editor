package infinityitemeditor.data.base;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.List;

public class DataListString extends DataList<DataString> {
    public DataListString() {
        super();
    }


    public DataListString(ListTag nbt) {
        super();
        nbt.forEach(this::add);
    }


    public DataListString(List<DataString> list) {
        super(list);
    }


    @Override
    public <T extends Tag> void add(T nbt) {
        if (nbt != null && nbt.getId() == Tag.TAG_STRING) {
            StringTag str = (StringTag) nbt;
            if (str != null && !str.getAsString().isEmpty()) {
                add(new DataString(str));
            }
        }
    }
}
