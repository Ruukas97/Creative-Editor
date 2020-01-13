package creativeeditor.data.types;

import net.minecraft.nbt.INBT;

public interface DataType<E> {
	public E get();
	public void set(E number);
	public INBT getNBT();
}
