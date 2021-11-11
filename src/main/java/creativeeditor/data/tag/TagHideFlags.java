package creativeeditor.data.tag;

import creativeeditor.data.base.DataBitField;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.IntNBT;

public class TagHideFlags extends DataBitField {
    public TagHideFlags(IntNBT value) {
        super(7, value);
    }

    public TagHideFlags(int value) {
        super(7, value);
    }

    public boolean getEnchantsHidden() {
        return data[ItemStack.TooltipDisplayFlags.ENCHANTMENTS.getMask()];
    }

    public void setEnchantsHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.ENCHANTMENTS.getMask()] = value;
    }

    public boolean getAttributesHidden() {
        return data[ItemStack.TooltipDisplayFlags.MODIFIERS.getMask()];
    }


    public void setAttributesHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.MODIFIERS.getMask()] = value;
    }

    public boolean getUnbreakableHidden() {
        return data[ItemStack.TooltipDisplayFlags.UNBREAKABLE.getMask()];
    }


    public void setUnbreakableHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.UNBREAKABLE.getMask()] = value;
    }

    public boolean getCanDestroyHidden() {
        return data[ItemStack.TooltipDisplayFlags.CAN_DESTROY.getMask()];
    }

    public void setCanDestroyHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.CAN_DESTROY.getMask()] = value;
    }


    public boolean getCanPlaceOnHidden() {
        return data[ItemStack.TooltipDisplayFlags.CAN_PLACE.getMask()];
    }

    public void setCanPlaceOnHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.CAN_PLACE.getMask()] = value;
    }

    public boolean getItemInfoHidden() {
        return data[ItemStack.TooltipDisplayFlags.ADDITIONAL.getMask()];
    }

    public void setItemInfoHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.ADDITIONAL.getMask()] = value;
    }

    public boolean getDyedHidden() {
        return data[ItemStack.TooltipDisplayFlags.DYE.getMask()];
    }


    public void getDyedHidden(boolean value) {
        data[ItemStack.TooltipDisplayFlags.DYE.getMask()] = value;
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
