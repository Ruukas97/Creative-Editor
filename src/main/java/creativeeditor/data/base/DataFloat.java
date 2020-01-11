package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.INumber;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;

public class DataFloat implements Data, INumber {
	private float value;

	public DataFloat(float value) {
		this.value = value;
	}

	public DataFloat(FloatNBT nbt) {
		this(nbt.getLong());
	}

	public float get() {
		return value;
	}

	@Override
	public Number getNumber() {
		return value;
	}

	@Override
	public void setNumber(Number number) {
		value = number.floatValue();
	}

	@Override
	public INBT getNBT() {
		return new FloatNBT(value);
	}
}
