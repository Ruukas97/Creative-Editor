package creativeeditor.data.tag;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

import creativeeditor.data.Data;
import creativeeditor.data.base.SingularData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagList<E extends Data<?, ?>> extends SingularData<List<E>, ListNBT> implements Iterable<E> {
    public TagList() {
        this( Lists.newArrayList() );
    }


    public TagList(ListNBT nbt) {
        this();
        nbt.forEach( this::add );
    }


    public TagList(List<E> list) {
        super( list );
    }


    public void add( E value ) {
        data.add( value );
    }


    public void add( INBT nbt ) {
        if (nbt.getId() == NBT.TAG_COMPOUND)
            add( (CompoundNBT) nbt );
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
