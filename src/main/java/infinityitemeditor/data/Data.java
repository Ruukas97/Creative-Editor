package infinityitemeditor.data;

import infinityitemeditor.data.base.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.*;

import javax.annotation.Nullable;

public interface Data<E, T extends Tag> {
    ChatFormatting SYNTAX_HIGHLIGHTING_KEY = ChatFormatting.AQUA;
    ChatFormatting SYNTAX_HIGHLIGHTING_STRING = ChatFormatting.GREEN;
    ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER = ChatFormatting.GOLD;
    ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = ChatFormatting.RED;

    E getData();


    /**
     * Returns whether or not this should be ignored, when saving data. If it has
     * the default value, we save space by not including it.
     *
     * @return true or false
     */
    boolean isDefault();


    /**
     * Creates an Tag object with this objects data
     *
     * @return created IBNT object
     */
    T getTag();


    static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch (ClassCastException e) {
            return null;
        }
    }


    /**
     * Creates a Data object from the given Tag
     *
     * @param nbt source to get data from
     * @return the created data object
     */
    @Nullable
    static Data<?, ?> getDataFromTag(Tag nbt) {
        switch (nbt.getId()) {
            case Tag.TAG_BYTE:
                return new DataByte((ByteTag) nbt);
            case Tag.TAG_BYTE_ARRAY:
                return new DataByteArray((ByteArrayTag) nbt);
            case Tag.TAG_COMPOUND:
                return new DataMap((CompoundTag) nbt);
            case Tag.TAG_DOUBLE:
                return new DataDouble((DoubleTag) nbt);
            case Tag.TAG_FLOAT:
                return new DataFloat((FloatTag) nbt);
            case Tag.TAG_INT:
                return new DataInteger((IntTag) nbt);
            case Tag.TAG_INT_ARRAY:
                return new DataIntegerArray((IntArrayTag) nbt);
            case Tag.TAG_LIST:
                return new DataListUncontrolled((ListTag) nbt);
            case Tag.TAG_LONG:
                return new DataLong((LongTag) nbt);
            case Tag.TAG_LONG_ARRAY:
                return new DataLongArray((LongArrayTag) nbt);
            case Tag.TAG_SHORT:
                return new DataShort((ShortTag) nbt);
            case Tag.TAG_STRING:
                return new DataString((StringTag) nbt);
            default:
                return null;
        }
    }


    /**
     * Returns true if the given Tag tag has the ID of a number value
     *
     * @param nbt The nbt to check
     * @return true or false
     */
    static boolean isNumber(Tag nbt) {
        int id = nbt.getId();
        return id == Tag.TAG_ANY_NUMERIC || (Tag.TAG_BYTE <= id && id <= Tag.TAG_DOUBLE);
    }

//    default MutableComponent getPrettyDisplay(String space, int indentantion){
//        return getTag().getPrettyDisplay(space, indentantion);
//    }
}
