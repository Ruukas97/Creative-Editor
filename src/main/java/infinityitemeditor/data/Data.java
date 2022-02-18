package infinityitemeditor.data;

import infinityitemeditor.data.base.*;
import infinityitemeditor.screen.nbt.INBTNode;
import net.minecraft.nbt.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nullable;

public interface Data<E, T extends INBT> extends INBTNode {
    TextFormatting SYNTAX_HIGHLIGHTING_KEY = TextFormatting.AQUA;
    TextFormatting SYNTAX_HIGHLIGHTING_STRING = TextFormatting.GREEN;
    TextFormatting SYNTAX_HIGHLIGHTING_NUMBER = TextFormatting.GOLD;
    TextFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = TextFormatting.RED;

    E getData();

    Data<?, ?> getParent();

    void setParent(Data<?, ?> parent);


    /**
     * Returns whether or not this should be ignored, when saving data. If it has
     * the default value, we save space by not including it.
     *
     * @return true or false
     */
    boolean isDefault();


    /**
     * Creates an INBT object with this objects data
     *
     * @return created IBNT object
     */
    T getNBT();


    static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch (ClassCastException e) {
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
    static Data<?, ?> getDataFromNBT(INBT nbt) {
        switch (nbt.getId()) {
            case NBT.TAG_BYTE:
                return new DataByte((ByteNBT) nbt);
            case NBT.TAG_BYTE_ARRAY:
                return new DataByteArray((ByteArrayNBT) nbt);
            case NBT.TAG_COMPOUND:
                return new DataMap((CompoundNBT) nbt);
            case NBT.TAG_DOUBLE:
                return new DataDouble((DoubleNBT) nbt);
            case NBT.TAG_FLOAT:
                return new DataFloat((FloatNBT) nbt);
            case NBT.TAG_INT:
                return new DataInteger((IntNBT) nbt);
            case NBT.TAG_INT_ARRAY:
                return new DataIntegerArray((IntArrayNBT) nbt);
            case NBT.TAG_LIST:
                return new DataListUncontrolled((ListNBT) nbt);
            case NBT.TAG_LONG:
                return new DataLong((LongNBT) nbt);
            case NBT.TAG_LONG_ARRAY:
                return new DataLongArray((LongArrayNBT) nbt);
            case NBT.TAG_SHORT:
                return new DataShort((ShortNBT) nbt);
            case NBT.TAG_STRING:
                return new DataString((StringNBT) nbt);
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
    static boolean isNumber(INBT nbt) {
        int id = nbt.getId();
        return id == NBT.TAG_ANY_NUMERIC || (NBT.TAG_BYTE <= id && id <= NBT.TAG_DOUBLE);
    }

    default ITextComponent getPrettyDisplay(String space, int indentation) {
        return getNBT().getPrettyDisplay(space, indentation);
    }
}
