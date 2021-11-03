package creativeeditor.data.base;

import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataLongArray extends SingularData<long[], LongArrayNBT> {

    public DataLongArray(long... values) {
        super(values);
    }


    public DataLongArray(LongArrayNBT nbt) {
        this(nbt.getAsLongArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public LongArrayNBT getNBT() {
        return new LongArrayNBT(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("L")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("[")).append(itextcomponent).append(";");

        for (int i = 0; i < this.data.length; ++i) {
            IFormattableTextComponent iformattabletextcomponent1 = (new StringTextComponent(String.valueOf(this.data[i]))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
            iformattabletextcomponent.append(" ").append(iformattabletextcomponent1).append(itextcomponent);
            if (i != this.data.length - 1) {
                iformattabletextcomponent.append(",");
            }
        }

        iformattabletextcomponent.append("]");
        return iformattabletextcomponent;
    }
}
