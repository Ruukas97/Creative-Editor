package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.INumber;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ShortNBT;

public class DataShort implements Data, INumber {
	private short value;

	public DataShort(short value) {
		this.value = value;
	}

	public DataShort(ShortNBT nbt) {
		this(nbt.getShort());
	}

	public short get() {
		return value;
	}

	@Override
	public Number getNumber() {
		return value;
	}

	@Override
	public void setNumber(Number number) {
		value = number.shortValue();
	}

	@Override
	public INBT getNBT() {
		return new ShortNBT(value);
	}

}
