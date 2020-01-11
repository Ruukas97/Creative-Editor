package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.INumber;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.LongNBT;

public class DataLong implements Data, INumber {
	private long value;

	public DataLong(long value) {
		this.value = value;
	}

	public DataLong(LongNBT nbt) {
		this(nbt.getLong());
	}

	public long get() {
		return value;
	}

	@Override
	public Number getNumber() {
		return value;
	}

	@Override
	public void setNumber(Number number) {
		value = number.longValue();
	}

	@Override
	public INBT getNBT() {
		return new LongNBT(value);
	}
}
