package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.INBT;

public class DataBoolean implements Data {
	private boolean value;
	
	public DataBoolean() {
		this(false);
	}

	public DataBoolean(boolean value) {
		this.value = value;
	}

	public DataBoolean(ByteNBT nbt) {
		this(nbt.getByte() != 0);
	}

	public boolean get() {
		return value;
	}

	public void set(boolean value) {
		this.value = value;
	}

	public void toggle() {
		set(!value);
	}

	@Override
	public INBT getNBT() {
		return new ByteNBT((byte) (value ? 1 : 0));
	}
}
