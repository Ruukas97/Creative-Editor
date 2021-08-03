package creativeeditor.data.tag;

import creativeeditor.data.base.DataBitField;
import net.minecraft.nbt.IntNBT;

public class TagHideFlags extends DataBitField {
    public static final int
    ENCHANTS = 0,
    ATTRIBUTES = 1,
    UNBREAKABLE = 2,
    CAN_DESTROY = 3,
    CAN_PLACE = 4,
    ITEM_INFO = 5,
    DYED = 6;

    public TagHideFlags(IntNBT value) {
        super(7, value);
    }
    public TagHideFlags(int value) {
        super(7, value);
    }

    public boolean getEnchantsHidden() {
        return data[ENCHANTS];
    }

    public void setEnchantsHidden(boolean value) {
        data[ENCHANTS] = value;
    }

    public boolean getAttributesHidden() {
        return data[ATTRIBUTES];
    }


    public void setAttributesHidden(boolean value) {
        data[ATTRIBUTES] = value;
    }

    public boolean getUnbreakableHidden() {
        return data[UNBREAKABLE];
    }


    public void setUnbreakableHidden(boolean value) {
        data[UNBREAKABLE] = value;
    }

    public boolean getCanDestroyHidden() {
        return data[CAN_DESTROY];
    }

    public void setCanDestroyHidden(boolean value) {
        data[CAN_DESTROY] = value;
    }


    public boolean getCanPlaceOnHidden() {
        return data[CAN_PLACE];
    }

    public void setCanPlaceOnHidden(boolean value) {
        data[CAN_PLACE] = value;
    }

    public boolean getItemInfoHidden() {
        return data[ITEM_INFO];
    }

    public void setItemInfoHidden(boolean value) {
        data[ITEM_INFO] = value;
    }

    public boolean getDyedHidden() {
        return data[DYED];
    }


    public void getDyedHidden(boolean value) {
        data[DYED] = value;
    }

    @Override
    public boolean isDefault() {
        return getInt() == 0;
    }


    @Override
    public IntNBT getNBT() {
        return IntNBT.valueOf(getInt());
    }
}
