package infinityitemeditor.data.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.render.NBTIcons;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public abstract class SingularData<E, T extends INBT> implements Data<E, T> {
    @Getter
    protected E data;

    @Getter
    @Setter
    protected Data<?, ?> parent;

    public SingularData(E data) {
        this.data = data;
    }


    /**
     * Gets the value of this Data object
     *
     * @return Data value
     */
    public E get() {
        return data;
    }


    /**
     * Sets the value of this Data object
     *
     * @param value Value to set to
     */
    public void set(E value) {
        data = value;
    }


    @Override
    public boolean equals(Object obj) {
        @SuppressWarnings("unchecked")
        SingularData<E, INBT> d = Data.convertInstanceOfObject(obj, this.getClass());
        return (d != null && d.data == this.data) || super.equals(obj);
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.EMPTY.renderIcon(mc, matrix, x, y);
    }
}
