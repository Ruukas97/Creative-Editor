package infinityitemeditor.data.base;

import net.minecraft.nbt.IntArrayTag;

public class DataIntegerArray extends SingularData<int[], IntArrayTag> {
    public DataIntegerArray(int... values) {
        super(values);
    }


    public DataIntegerArray(IntArrayTag nbt) {
        this(nbt.getAsIntArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public IntArrayTag getTag() {
        return new IntArrayTag(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("I")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        IFormattableTextComponent iformattabletextcomponent = (new TextComponent("[")).append(itextcomponent).append(";");
//
//        for (int i = 0; i < this.data.length; ++i) {
//            iformattabletextcomponent.append(" ").append((new TextComponent(String.valueOf(this.data[i]))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER));
//            if (i != this.data.length - 1) {
//                iformattabletextcomponent.append(",");
//            }
//        }
//
//        iformattabletextcomponent.append("]");
//        return iformattabletextcomponent;
//    }
}
