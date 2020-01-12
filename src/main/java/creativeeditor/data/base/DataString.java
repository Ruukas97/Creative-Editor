package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;

public class DataString implements Data {
	private String value;
	
	public DataString() {
		this("");
	}

	public DataString(String value) {
		set(value);
	}
	
	public DataString(StringNBT nbt) {
		this(nbt.getString());
	}

	public String get() {
		return value;
	}

	public void set(String value) {
		this.value = value != null ? value : "";
	}
	
	@Override
	public boolean isDefault() {
		return value.isEmpty();
	}

	@Override
	public INBT getNBT() {
		return new StringNBT(value);
	}

}
