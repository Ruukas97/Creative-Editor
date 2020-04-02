package creativeeditor.data;

import javax.annotation.Nullable;

import creativeeditor.data.base.DataByte;
import creativeeditor.data.base.DataByteArray;
import creativeeditor.data.base.DataDouble;
import creativeeditor.data.base.DataFloat;
import creativeeditor.data.base.DataInteger;
import creativeeditor.data.base.DataIntegerArray;
import creativeeditor.data.base.DataListUncontrolled;
import creativeeditor.data.base.DataLong;
import creativeeditor.data.base.DataLongArray;
import creativeeditor.data.base.DataMap;
import creativeeditor.data.base.DataShort;
import creativeeditor.data.base.DataString;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.Constants.NBT;

public interface Data<E, T extends INBT> {
    public E getData();


    /**
     * Returns whether or not this should be ignored, when saving data. If it has
     * the default value, we save space by not including it.
     * 
     * @return true or false
     */
    public boolean isDefault();


    /**
     * Creates an INBT object with this objects data
     * 
     * @return created IBNT object
     */
    public T getNBT();


    public static <T> T convertInstanceOfObject( Object o, Class<T> clazz ) {
        try {
            return clazz.cast( o );
        }
        catch (ClassCastException e) {
            return null;
        }
    }


    /**
     * Creates a Data object from the given INBT
     * 
     * @param nbt source to get data from
     * @return the created data object
     */
    @Nullable
    public static Data<?, ?> getDataFromNBT( INBT nbt ) {
        switch (nbt.getId()) {
        case NBT.TAG_BYTE:
            return new DataByte( (ByteNBT) nbt );
        case NBT.TAG_BYTE_ARRAY:
            return new DataByteArray( (ByteArrayNBT) nbt );
        case NBT.TAG_COMPOUND:
            return new DataMap( (CompoundNBT) nbt );
        case NBT.TAG_DOUBLE:
            return new DataDouble( (DoubleNBT) nbt );
        case NBT.TAG_FLOAT:
            return new DataFloat( (FloatNBT) nbt );
        case NBT.TAG_INT:
            return new DataInteger( (IntNBT) nbt );
        case NBT.TAG_INT_ARRAY:
            return new DataIntegerArray( (IntArrayNBT) nbt );
        case NBT.TAG_LIST:
            return new DataListUncontrolled( (ListNBT) nbt );
        case NBT.TAG_LONG:
            return new DataLong( (LongNBT) nbt );
        case NBT.TAG_LONG_ARRAY:
            return new DataLongArray( (LongArrayNBT) nbt );
        case NBT.TAG_SHORT:
            return new DataShort( (ShortNBT) nbt );
        case NBT.TAG_STRING:
            return new DataString( (StringNBT) nbt );
        default:
            return null;
        }
    }


    /**
     * Returns true if the given NBT tag has the ID of a number value
     * 
     * @param nbt The nbt to check
     * @return true or false
     */
    public static boolean isNumber( INBT nbt ) {
        int id = nbt.getId();
        return id == NBT.TAG_ANY_NUMERIC || (NBT.TAG_BYTE <= id && id <= NBT.TAG_DOUBLE);
    }
}
