package infinityitemeditor.data.base;

import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataShort extends SingularData<Short, ShortNBT> {
    public DataShort() {
        this((short) 0);
    }


    public DataShort(short value) {
        super(value);
    }


    public DataShort(ShortNBT nbt) {
        this(nbt.getAsShort());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public ShortNBT getNBT() {
        return ShortNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("s")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return (new StringTextComponent(String.valueOf((int) this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }
}
