package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.widgets.StyledToggle;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.INBT;

public class DataBoolean implements Data, IPressable {
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
	public boolean isDefault() {
		return !value;
	}

	@Override
	public INBT getNBT() {
		return new ByteNBT((byte) (value ? 1 : 0));
	}

	@Override
	public void onPress(Button button) {
		toggle();
		if (button instanceof StyledToggle) {
			((StyledToggle) button).updateMessage(value);
		}
	}
}
