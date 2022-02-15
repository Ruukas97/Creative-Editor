package infinityitemeditor.data.base;

import net.minecraft.nbt.IntTag;

public class DataInteger extends SingularData<Integer, IntTag> {
    public DataInteger() {
        this(0);
    }


    public DataInteger(IntTag nbt) {
        this(nbt.getAsInt());
    }


    public DataInteger(int value) {
        super(value);
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public IntTag getTag() {
        return IntTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return (new TextComponent(String.valueOf(this.data))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }
}
