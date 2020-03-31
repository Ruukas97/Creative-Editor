package creativeeditor.data.tag;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

import creativeeditor.data.base.SingularData;
import creativeeditor.data.tag.TagEnchantment;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagEnchantments extends SingularData<List<TagEnchantment>, ListNBT> implements Iterable<TagEnchantment> {
    public TagEnchantments() {
        this( Lists.newArrayList() );
    }


    public TagEnchantments(ListNBT nbt) {
        this();
        nbt.forEach( this::add );
    }


    public TagEnchantments(List<TagEnchantment> list) {
        super( list );
    }


    public void add( TagEnchantment value ) {
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
    public ListIterator<TagEnchantment> iterator() {
        return data.listIterator();
    }
}
