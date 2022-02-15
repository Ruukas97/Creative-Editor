package infinityitemeditor.data.base;

import net.minecraft.nbt.ShortTag;

public class DataShort extends SingularData<Short, ShortTag> {
    public DataShort() {
        this((short) 0);
    }


    public DataShort(short value) {
        super(value);
    }


    public DataShort(ShortTag nbt) {
        this(nbt.getAsShort());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public ShortTag getTag() {
        return ShortTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("s")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        return (new TextComponent(String.valueOf((int) this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }
}
