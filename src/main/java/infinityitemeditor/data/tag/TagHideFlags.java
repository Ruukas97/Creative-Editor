package infinityitemeditor.data.tag;

import infinityitemeditor.data.base.DataBitField;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.item.ItemStack;

public class TagHideFlags extends DataBitField {
    public TagHideFlags(IntTag value) {
        super(7, value);
    }

    public TagHideFlags(int value) {
        super(7, value);
    }

    public boolean getEnchantsHidden() {
        return data[ItemStack.TooltipPart.ENCHANTMENTS.getMask()];
    }

    public void setEnchantsHidden(boolean value) {
        data[ItemStack.TooltipPart.ENCHANTMENTS.getMask()] = value;
    }

    public boolean getAttributesHidden() {
        return data[ItemStack.TooltipPart.MODIFIERS.getMask()];
    }


    public void setAttributesHidden(boolean value) {
        data[ItemStack.TooltipPart.MODIFIERS.getMask()] = value;
    }

    public boolean getUnbreakableHidden() {
        return data[ItemStack.TooltipPart.UNBREAKABLE.getMask()];
    }


    public void setUnbreakableHidden(boolean value) {
        data[ItemStack.TooltipPart.UNBREAKABLE.getMask()] = value;
    }

    public boolean getCanDestroyHidden() {
        return data[ItemStack.TooltipPart.CAN_DESTROY.getMask()];
    }

    public void setCanDestroyHidden(boolean value) {
        data[ItemStack.TooltipPart.CAN_DESTROY.getMask()] = value;
    }


    public boolean getCanPlaceOnHidden() {
        return data[ItemStack.TooltipPart.CAN_PLACE.getMask()];
    }

    public void setCanPlaceOnHidden(boolean value) {
        data[ItemStack.TooltipPart.CAN_PLACE.getMask()] = value;
    }

    public boolean getItemInfoHidden() {
        return data[ItemStack.TooltipPart.ADDITIONAL.getMask()];
    }

    public void setItemInfoHidden(boolean value) {
        data[ItemStack.TooltipPart.ADDITIONAL.getMask()] = value;
    }

    public boolean getDyedHidden() {
        return data[ItemStack.TooltipPart.DYE.getMask()];
    }


    public void getDyedHidden(boolean value) {
        data[ItemStack.TooltipPart.DYE.getMask()] = value;
    }

    @Override
    public boolean isDefault() {
        return getInt() == 0;
    }


    @Override
    public IntTag getTag() {
        return IntTag.valueOf(getInt());
    }
}
