package creativeeditor.data.base;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import creativeeditor.data.Data;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class DataMap extends SingularData<Map<String, Data<?, ?>>, CompoundNBT> {

	public DataMap() {
		this(Maps.newHashMap());
	}

	public DataMap(Map<String, Data<?, ?>> map) {
		super(map);
	}

	public DataMap(CompoundNBT nbt) {
		this();
		if (nbt == null || nbt.isEmpty())
			return;
		for (String key : nbt.keySet()) {
			INBT value = nbt.get(key);
			if (value != null)
				put(key, Data.getDataFromNBT(value));
		}
	}

	@Nullable
	public Data<?, ?> getData(String key) {
		return data.get(key);
	}

	@Nonnull
	public Data<?, ?> getDataDefaulted(String key, @Nonnull Data<?, ?> defaultValue) {
		if (!data.containsKey(key)) {
			Data<?, ?> dat = data.get(key);
			if (dat != null)
				return dat;
		}

		put(key, defaultValue);
		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	public <T extends Data<?, ?>> T getDataDefaultedForced(String key, @Nonnull T defaultValue) {
		if (data.containsKey(key)) {
			Data<?, ?> existing = data.get(key);
			if (existing.getClass() == defaultValue.getClass()) {
				return (T) existing;
			}
		}

		put(key, defaultValue);
		return defaultValue;
	}

	public void put(String key, Data<?, ?> value) {
		if (value != null)
			data.put(key, value);
	}

	public void clear() {
		data.clear();
	}

	@Override
	public boolean isDefault() {
		if (data.isEmpty())
			return true;

		for (Entry<String, Data<?, ?>> entry : data.entrySet()) {
			if (!entry.getValue().isDefault())
				return false;
		}

		return true;
	}

	public CompoundNBT getNBTIncludeAll() {
		CompoundNBT nbt = new CompoundNBT();
		data.forEach((key, value) -> {
			if (value instanceof DataMap)
				nbt.put(key, ((DataMap) value).getNBTIncludeAll());
			else
				nbt.put(key, value.getNBT());
		});
		return nbt;
	}

	@Override
	public CompoundNBT getNBT() {
		CompoundNBT nbt = new CompoundNBT();
		data.forEach((key, value) -> {
			if (!value.isDefault())
				nbt.put(key, value.getNBT());
		});
		return nbt;
	}
}
