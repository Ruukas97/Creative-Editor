package creativeeditor.data.base;

import net.minecraft.nbt.IntNBT;

public class DataBitField extends SingularData<boolean[], IntNBT> {
	private boolean keepSize;

	public DataBitField(boolean keepSize, boolean... data) {
		super(data);
		this.keepSize = keepSize;
	}

	@Override
	public void set(boolean[] value) {
		if(keepSize && value.length != data.length) {
			for(int i=0; i<value.length && i<data.length; i++) {
				data[i] = value[i];
			}
		}
		else super.set(value);
	}

	@Override
	public DataBitField copy() {
		return new DataBitField(keepSize, data.clone());
	}

	@Override
	public boolean isDefault() {
		return getInt() == 0;
	}

	public int getInt() {
		int result = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i]) {
				result += 1 << i;
			}
		}
		return result;
	}

	public void setInt(int value) {
		for (int i = 0; i < data.length; i++) {
			data[i] = (value & (1 << i)) != 0;
		}
	}

	@Override
	public IntNBT getNBT() {
		return new IntNBT(getInt());
	}

}
