package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.data.INumber;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.INBT;

public class DataByte implements Data, INumber {
	private byte value;

	public DataByte(byte value) {
		this.value = value;
	}

	public DataByte(ByteNBT nbt) {
		this(nbt.getByte());
	}
	
	public byte get() {
		return value;
	}

	@Override
	public Number getNumber() {
		return value;
	}

	@Override
	public void setNumber(Number number) {
		value = number.byteValue();
	}

	@Override
	public INBT getNBT() {
		return new ByteNBT(value);
	}
}
