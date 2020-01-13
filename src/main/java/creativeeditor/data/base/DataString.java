package creativeeditor.data.base;

import creativeeditor.data.Data;
import net.minecraft.nbt.StringNBT;

public class DataString extends SingularData<String, StringNBT> {
	
	public DataString() {
		this("");
	}

	public DataString(String value) {
		super(value);
	}
	
	public DataString(StringNBT nbt) {
		this(nbt.getString());
	}

	public void set(String value) {
		data = value != null ? value : "";
	}

	@Override
	public Data<String, StringNBT> copy() {
		return new DataString(new String(data));
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public StringNBT getNBT() {
		return null;
	}
}
