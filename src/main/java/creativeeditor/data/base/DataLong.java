package creativeeditor.data.base;

import net.minecraft.nbt.LongNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataLong extends SingularData<Long, LongNBT> {

    public DataLong() {
        this(0);
    }


    public DataLong(long value) {
        super(value);
    }


    public DataLong(LongNBT nbt) {
        this(nbt.getAsLong());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public LongNBT getNBT() {
        return LongNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("L")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return (new StringTextComponent(String.valueOf(this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }
}
