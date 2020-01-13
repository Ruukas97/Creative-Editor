package creativeeditor.data.base;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class DataList extends SingularData<List<Data<?, ?>>, ListNBT> implements Iterable<Data<?, ?>> {
	public DataList() {
		this(Lists.newArrayList());
	}

	public DataList(ListNBT nbt) {
		this();
		nbt.forEach(this::add);
	}

	public DataList(List<Data<?, ?>> list) {
		super(list);
	}

	public void add(Data<?, ?> value) {
		data.add(value);
	}

	public void add(INBT nbt) {
		Data<?, ?> d = Data.fromNBT(nbt);
		if (d != null) {
			this.add(d);
		}
	}

	public void clear() {
		data.clear();
	}

	@Override
	public boolean isDefault() {
		return data.isEmpty();
	}

	@Override
	public Data<List<Data<?, ?>>, ListNBT> copy() {
		return new DataList(Lists.newArrayList(data));
	}

	@Override
	public ListNBT getNBT() {
		ListNBT nbt = new ListNBT();
		data.forEach(dat -> {
			if (!dat.isDefault())
				nbt.add(dat.getNBT());
		});
		return nbt;
	}

	@Override
	public ListIterator<Data<?, ?>> iterator() {
		return data.listIterator();
	}
}
