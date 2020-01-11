package creativeeditor.data.base;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import creativeeditor.data.Data;
import creativeeditor.data.KeyTag;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class DataMap implements Data {
	private Map<String, Data> map;

	public DataMap() {
		map = Maps.newHashMap();
	}

	public DataMap(KeyTag... keyTags) {
		map = Maps.newHashMap();
		for (KeyTag tag : keyTags) {
			put(tag.key, tag.value);
		}
	}

	public DataMap(CompoundNBT nbt) {
		map = Maps.newHashMap();
		Set<String> keys = nbt.keySet();
		keys.forEach(key -> {
			Data data = Data.fromNBT(nbt.get(key));
			if (data != null)
				put(key, data);
		});
	}

	public Map<String, Data> get() {
		return map;
	}

	public void put(String key, Data value) {
		map.put(key, value);
	}

	public void clear() {
		map.clear();
	}

	@Override
	public INBT getNBT() {
		CompoundNBT nbt = new CompoundNBT();
		map.forEach((key, value) -> nbt.put(key, value.getNBT()));
		return nbt;
	}
}
