package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.INBT;

public class DataByteArray implements Data {
	private byte[] values;

	public DataByteArray(byte... values) {
		this.values = values;
	}

	public DataByteArray(ByteArrayNBT nbt) {
		this(nbt.getByteArray());
	}

	public byte[] get() {
		return values;
	}
	
	@Override
	public boolean isDefault() {
		return values.length == 0;
	}

	@Override
	public INBT getNBT() {
		return new ByteArrayNBT(values);
	}
}
