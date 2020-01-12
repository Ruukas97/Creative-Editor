package creativeeditor.data.base;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class DataList implements Data, Iterable<Data> {
	private List<Data> list;

	public DataList() {
		list = Lists.newArrayList();
	}

	public DataList(Data... data) {
		list = Lists.newArrayList(data);
	}

	public DataList(ListNBT nbt) {
		list = Lists.newArrayList();
		nbt.forEach(this::add);
	}

	public List<Data> get() {
		return list;
	}

	public void add(Data data) {
		list.add(data);
	}

	public void add(INBT nbt) {
		Data data = Data.fromNBT(nbt);
		if (data != null)
			list.add(data);
	}

	public void clear() {
		list.clear();
	}
	
	@Override
	public boolean isDefault() {
		return list.isEmpty();
	}
	
	@Override
	public Iterator<Data> iterator() {
		return list.listIterator();
	}

	@Override
	public INBT getNBT() {
		ListNBT nbt = new ListNBT();
		list.forEach(data -> {
			if(!data.isDefault())
				nbt.add(data.getNBT());
		});
		return nbt;
	}
}
