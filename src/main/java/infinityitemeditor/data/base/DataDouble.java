package infinityitemeditor.data.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.render.NBTIcons;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataDouble extends SingularData<Double, DoubleNBT> {
    public DataDouble(double value) {
        super(value);
    }


    public DataDouble(DoubleNBT nbt) {
        this(nbt.getAsDouble());
    }


    @Override
    public boolean isDefault() {
        return data == 0;
    }


    @Override
    public DoubleNBT getNBT() {
        return DoubleNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("d")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return (new StringTextComponent(String.valueOf(this.data))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.DOUBLE.renderIcon(mc, matrix, x, y);
    }
}
