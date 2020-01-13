package creativeeditor.data.types;

import net.minecraft.nbt.IntNBT;

public class IntegerType implements DataType<Integer> {
	public int value;
	
	public IntegerType(int value) {
		this.value = value;
	}
	
	@Override
	public Integer get() {
		return value;
	}

	@Override
	public void set(Integer number) {
		this.value = number;
	}

	@Override
	public IntNBT getNBT() {
		return new IntNBT(value);
	}
}
