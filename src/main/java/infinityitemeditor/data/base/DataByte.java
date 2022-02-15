package infinityitemeditor.data.base;

import net.minecraft.nbt.ByteTag;

public class DataByte extends SingularData<Byte, ByteTag> {
    public DataByte(byte value) {
        super(value);
    }


    public DataByte(ByteTag nbt) {
        this(nbt.getAsByte());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public ByteTag getTag() {
        return ByteTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("b")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        return (new TextComponent(String.valueOf((int)this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }
}
