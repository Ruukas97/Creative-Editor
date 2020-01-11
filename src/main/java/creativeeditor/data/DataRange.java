package creativeeditor.data;

import creativeeditor.data.base.DataInteger;
import net.minecraft.nbt.INBT;
import net.minecraft.util.math.MathHelper;

public class DataRange implements Data, INumber {
	private INumber number;
	private int min, max;

	public DataRange(int value, int min, int max) {
		number = new DataInteger(value);
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
	
	@Override
	public Number getNumber() {
		return number.getNumber();
	}

	@Override
	public void setNumber(Number number) {
		this.number.setNumber(MathHelper.clamp(number.intValue(), min, max));
	}


	@Override
	public INBT getNBT() {
		if(number != null && number instanceof Data) {
			return ((Data)number).getNBT();
		}
		return null;
	}
}
