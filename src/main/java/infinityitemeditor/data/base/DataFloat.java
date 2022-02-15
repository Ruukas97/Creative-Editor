package infinityitemeditor.data.base;

import net.minecraft.nbt.FloatTag;

public class DataFloat extends SingularData<Float, FloatTag> {

    public DataFloat(float value) {
        super(value);
    }


    public DataFloat(FloatTag nbt) {
        this(nbt.getAsLong());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public FloatTag getTag() {
        return FloatTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent("f")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        return (new TextComponent(String.valueOf(this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }
}
