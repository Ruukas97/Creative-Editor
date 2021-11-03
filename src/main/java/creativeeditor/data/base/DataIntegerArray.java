package creativeeditor.data.base;

import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataIntegerArray extends SingularData<int[], IntArrayNBT> {
    public DataIntegerArray(int... values) {
        super(values);
    }


    public DataIntegerArray(IntArrayNBT nbt) {
        this(nbt.getAsIntArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public IntArrayNBT getNBT() {
        return new IntArrayNBT(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("I")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("[")).append(itextcomponent).append(";");

        for (int i = 0; i < this.data.length; ++i) {
            iformattabletextcomponent.append(" ").append((new StringTextComponent(String.valueOf(this.data[i]))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER));
            if (i != this.data.length - 1) {
                iformattabletextcomponent.append(",");
            }
        }

        iformattabletextcomponent.append("]");
        return iformattabletextcomponent;
    }
}
