package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.LongArrayNBT;

public class DataLongArray implements Data {
	private long[] values;

	public DataLongArray(long... values) {
		this.values = values;
	}

	public DataLongArray(LongArrayNBT nbt) {
		this(nbt.getAsLongArray());
	}

	public long[] get() {
		return values;
	}

	@Override
	public INBT getNBT() {
		return new LongArrayNBT(values);
	}
}
