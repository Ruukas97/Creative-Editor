package infinityitemeditor.data.base;

import net.minecraft.nbt.LongTag;

public class DataLong extends SingularData<Long, LongTag> {

    public DataLong() {
        this(0);
    }


    public DataLong(long value) {
        super(value);
    }


    public DataLong(LongTag nbt) {
        this(nbt.getAsLong());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public LongTag getTag() {
        return LongTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("L")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        return (new TextComponent(String.valueOf(this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }
}
