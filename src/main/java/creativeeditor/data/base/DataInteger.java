package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.INumber;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;

public class DataInteger implements Data, INumber {
	private int value;
	
	public DataInteger() {
		this(0);
	}

	public DataInteger(int value) {
		this.value = value;
	}

	public DataInteger(IntNBT nbt) {
		this(nbt.getInt());
	}

	public int get() {
		return value;
	}

	@Override
	public Number getNumber() {
		return value;
	}

	@Override
	public void setNumber(Number number) {
		value = number.intValue();
	}

	@Override
	public INBT getNBT() {
		return new IntNBT(value);
	}
}
