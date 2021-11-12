package infinityitemeditor.data.base;

import net.minecraft.nbt.FloatNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataFloat extends SingularData<Float, FloatNBT> {

    public DataFloat(float value) {
        super(value);
    }


    public DataFloat(FloatNBT nbt) {
        this(nbt.getAsLong());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public FloatNBT getNBT() {
        return FloatNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("f")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return (new StringTextComponent(String.valueOf(this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }
}
