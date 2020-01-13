package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;

public abstract class SingularData<E, T extends INBT> implements Data<E, T> {
	protected E data;

	public SingularData(E data) {
		this.data = data;
	}

	@Override
	public E get() {
		return data;
	}

	@Override
	public void set(E value) {
		this.data = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		SingularData<E, INBT> d = Data.convertInstanceOfObject(obj, this.getClass());
		return (d != null && d.data == this.data) || super.equals(obj);
	}
}
