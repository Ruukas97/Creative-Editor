package infinityitemeditor.data.base;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataInteger extends SingularData<Integer, IntNBT> {
    public DataInteger() {
        this(0);
    }


    public DataInteger(IntNBT nbt) {
        this(nbt.getAsInt());
    }


    public DataInteger(int value) {
        super(value);
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public IntNBT getNBT() {
        return IntNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return (new StringTextComponent(String.valueOf(this.data))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }
}
