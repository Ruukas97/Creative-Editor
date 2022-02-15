package infinityitemeditor.data.base;

import net.minecraft.nbt.DoubleTag;

public class DataDouble extends SingularData<Double, DoubleTag> {
    public DataDouble(double value) {
        super(value);
    }


    public DataDouble(DoubleTag nbt) {
        this(nbt.getAsDouble());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public DoubleTag getTag() {
        return DoubleTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("d")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        return (new TextComponent(String.valueOf(this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }
}
