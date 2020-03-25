package creativeeditor.data;

import creativeeditor.data.base.DataInteger;
import net.minecraft.util.math.MathHelper;

public class NumberRange extends DataInteger {
	protected int min, max;

	public NumberRange(int number, int min, int max) {
		super(number);
		this.min = min;
		this.max = max;
	}

	public NumberRange(int min, int max) {
		this(min, min, max);
	}

	@Override
	public void set(Integer number) {
		super.set(MathHelper.clamp(number, getMin(), getMax()));
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
}
