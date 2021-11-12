package infinityitemeditor.data.base;

import infinityitemeditor.data.Data;
import lombok.Getter;
import net.minecraft.nbt.INBT;

public abstract class SingularData<E, T extends INBT> implements Data<E, T> {
    @Getter
    protected E data;

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
}
