package creativeeditor.data.tag;

import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.SingularData;
import net.minecraft.nbt.IntNBT;

public class TagHideFlags extends SingularData<TagHideFlags.HideFlags, IntNBT> {
    public TagHideFlags(HideFlags hideFlags) {
        super(hideFlags);
    }

    public DataBoolean getEnchantsHidden() {
        return new DataBoolean(data.enchants);
    }


    public void setEnchantsHidden(boolean value) {
        data.enchants = value;
    }


    public DataBoolean getAttributesHidden() {
        return new DataBoolean(data.attributes);
    }


    public void setAttributesHidden(boolean value) {
        data.attributes = value;
    }


    public boolean getUnbreakableHidden() {
        return data.unbreakable;
    }


    public void setUnbreakableHidden(boolean value) {
        data.unbreakable = value;
    }


    public boolean getCanDestroyHidden() {
        return data.canDestroy;
    }


    public void setCanDestroyHidden(boolean value) {
        data.canDestroy = value;
    }


    public boolean getCanPlaceOnHidden() {
        return data.canPlaceOn;
    }


    public void setCanPlaceOnHidden(boolean value) {
        data.canPlaceOn = value;
    }


    public boolean getItemInfoHidden() {
        return data.itemInfo;
    }


    public void setItemInfoHidden(boolean value) {
        data.itemInfo = value;
    }


    public int getInt() {
        int result = 0;
        if (getEnchantsHidden().get())
            result += 1;
        if (getAttributesHidden().get())
            result += 2;
        if (getUnbreakableHidden())
            result += 4;
        if (getCanDestroyHidden())
            result += 8;
        if (getCanPlaceOnHidden())
            result += 16;
        if (getItemInfoHidden())
            result += 32;
        return result;
    }

    public static HideFlags getFlags(int i) {
        boolean enchants, attributes, unbreakable, canDestroy, canPlaceOn, itemInfo;
        enchants = attributes = unbreakable = canDestroy = canPlaceOn = itemInfo = false;
        if(i >= 32) {
            i -= 32;
            itemInfo = true;
        }
        if(i >= 16) {
            i -= 16;
            canPlaceOn = true;
        }
        if(i >= 8) {
            i -= 8;
            canDestroy = true;
        }
        if(i >= 4) {
            i -= 4;
            unbreakable = true;
        }
        if(i >= 2) {
            i -= 2;
            attributes = true;
        }
        if(i >= 1) {
            i -= 1;
            enchants = true;
        }
        return new HideFlags(enchants, attributes, unbreakable, canDestroy, canPlaceOn, itemInfo);
    }


    @Override
    public boolean isDefault() {
        return getInt() == 0;
    }


    @Override
    public IntNBT getNBT() {
        return IntNBT.valueOf(getInt());
    }


    public static class HideFlags {
        public boolean enchants, attributes, unbreakable, canDestroy, canPlaceOn, itemInfo;


        public HideFlags(boolean enchants, boolean attributes, boolean unbreakable, boolean canDestroy, boolean canPlaceOn, boolean itemInfo) {
            this.enchants = enchants;
            this.attributes = attributes;
            this.unbreakable = unbreakable;
            this.canDestroy = canDestroy;
            this.canPlaceOn = canPlaceOn;
            this.itemInfo = itemInfo;
        }


        public HideFlags() {
            this(false, false, false, false, false, false);
        }
    }
}
