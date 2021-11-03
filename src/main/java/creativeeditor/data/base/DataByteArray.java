package creativeeditor.data.base;

import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataByteArray extends SingularData<byte[], ByteArrayNBT> {
    public DataByteArray(byte... values) {
        super(values);
    }


    public DataByteArray(ByteArrayNBT nbt) {
        this(nbt.getAsByteArray());
    }


    @Override
    public boolean isDefault() {
        return data.length == 0;
    }


    @Override
    public ByteArrayNBT getNBT() {
        return new ByteArrayNBT(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("B")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("[")).append(itextcomponent).append(";");

        for (int i = 0; i < this.data.length; ++i) {
            IFormattableTextComponent iformattabletextcomponent1 = (new StringTextComponent(String.valueOf((int) this.data[i]))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
            iformattabletextcomponent.append(" ").append(iformattabletextcomponent1).append(itextcomponent);
            if (i != this.data.length - 1) {
                iformattabletextcomponent.append(",");
            }
        }

        iformattabletextcomponent.append("]");
        return iformattabletextcomponent;
    }
}
