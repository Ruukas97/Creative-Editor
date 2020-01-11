package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.INumber;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;

public class DataDouble implements Data, INumber {
	private double value;

	public DataDouble(double value) {
		this.value = value;
	}
	
	public DataDouble(DoubleNBT nbt) {
		this(nbt.getDouble());
	}
	
	public double get() {
		return value;
	}
	
	@Override
	public Number getNumber() {
		return value;
	}

	@Override
	public void setNumber(Number number) {
		value = number.doubleValue();
	}

	@Override
	public INBT getNBT() {
		return new DoubleNBT(value);
	}
}
