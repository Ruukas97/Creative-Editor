package creativeeditor.data.tag;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;

public abstract class KeyTag implements Data {
	protected String key;

	public KeyTag(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public abstract INBT getNBT();
}
