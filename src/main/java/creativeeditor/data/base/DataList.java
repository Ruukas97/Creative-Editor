package creativeeditor.data.base;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

import creativeeditor.data.Data;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public abstract class DataList<E extends Data<?, ?>>extends SingularData<List<E>, ListNBT> implements Iterable<E> {
    public DataList() {
        this( Lists.newArrayList() );
    }


    public DataList(ListNBT nbt) {
        this();
        nbt.forEach( this::add );
    }


    public DataList(List<E> list) {
        super( list );
    }


    public void add( E value ) {
        data.add( value );
    }


    public abstract <T extends INBT> void add( T nbt );

    public void remove( E value ){
        data.remove(value);
    }

    public void remove( int index ){
        data.remove(index);
    }

    public void clear() {
        data.clear();
    }


    @Override
    public boolean isDefault() {
        return data.isEmpty();
    }


    @Override
    public ListNBT getNBT() {
        ListNBT nbt = new ListNBT();
        data.forEach( dat -> {
            if (!dat.isDefault())
                nbt.add( dat.getNBT() );
        } );
        return nbt;
    }


    @Override
    public ListIterator<E> iterator() {
        return data.listIterator();
    }
}
