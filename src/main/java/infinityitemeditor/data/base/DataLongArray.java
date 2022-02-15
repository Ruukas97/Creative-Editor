package infinityitemeditor.data.base;

import net.minecraft.nbt.LongArrayTag;

public class DataLongArray extends SingularData<long[], LongArrayTag> {

    public DataLongArray(long... values) {
        super(values);
    }


    public DataLongArray(LongArrayTag nbt) {
        this(nbt.getAsLongArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public LongArrayTag getTag() {
        return new LongArrayTag(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("L")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        IFormattableTextComponent iformattabletextcomponent = (new TextComponent("[")).append(itextcomponent).append(";");
//
//        for (int i = 0; i < this.data.length; ++i) {
//            IFormattableTextComponent iformattabletextcomponent1 = (new TextComponent(String.valueOf(this.data[i]))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
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
