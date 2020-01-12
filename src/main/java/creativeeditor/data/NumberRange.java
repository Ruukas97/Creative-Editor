package creativeeditor.data;

import creativeeditor.data.base.DataInteger;
import creativeeditor.widgets.StyledSlider;
import creativeeditor.widgets.StyledSlider.SliderHandler;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.math.MathHelper;

public class NumberRange implements Data, INumber, SliderHandler {
	private INumber number;
	private int min, max;

	public NumberRange(INumber number, int min, int max) {
		this.number = number;
		this.min = min;
		this.max = max;
	}

	public NumberRange(int value, int min, int max) {
		this(new DataInteger(value), min, max);
	}

	public NumberRange(int min, int max) {
		this(min, min, max);
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public KeyTag key(String key) {
		return new KeyTag(key, this);
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
	public void onSlideValue(StyledSlider slider) {
		setNumber(slider.value);
	}

	@Override
	public INBT getNBT() {
		if (number != null && number instanceof Data) {
			return ((Data) number).getNBT();
		}
		return new IntNBT(0);
	}
}
