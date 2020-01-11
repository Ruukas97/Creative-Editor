package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;

public class DataIntegerArray implements Data {
	private int[] values;

	public DataIntegerArray(int... values) {
		this.values = values;
	}

	public DataIntegerArray(IntArrayNBT nbt) {
		this(nbt.getIntArray());
	}

	public int[] get() {
		return values;
	}

	@Override
	public INBT getNBT() {
		return new IntArrayNBT(values);
	}
}
