package infinityitemeditor.data.base;

import net.minecraft.nbt.ByteArrayTag;

public class DataByteArray extends SingularData<byte[], ByteArrayTag> {
    public DataByteArray(byte... values) {
        super(values);
    }


    public DataByteArray(ByteArrayTag nbt) {
        this(nbt.getAsByteArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public ByteArrayTag getTag() {
        return new ByteArrayTag(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("B")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        IFormattableTextComponent iformattabletextcomponent = (new TextComponent("[")).append(itextcomponent).append(";");
//
//        for (int i = 0; i < this.data.length; ++i) {
//            IFormattableTextComponent iformattabletextcomponent1 = (new TextComponent(String.valueOf((int) this.data[i]))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//            iformattabletextcomponent.append(" ").append(iformattabletextcomponent1).append(itextcomponent);
//            if (i != this.data.length - 1) {
//                iformattabletextcomponent.append(",");
//            }
//        }
//
//        iformattabletextcomponent.append("]");
//        return iformattabletextcomponent;
//    }
}
