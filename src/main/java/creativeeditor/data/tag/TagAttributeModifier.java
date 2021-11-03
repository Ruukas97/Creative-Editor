package creativeeditor.data.tag;

import creativeeditor.data.base.SingularData;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TagAttributeModifier extends SingularData<AttributeModifier, CompoundNBT> {
    /**
     * See: {@link }
     * {@link ItemStack#(String, AttributeModifier, EquipmentSlotType)}
     * {@link }
     */

    @Getter
    @Setter
    private String name;

    @Nullable
    @Getter
    @Setter
    private EquipmentSlotType slot;


    public TagAttributeModifier(String name, AttributeModifier mod, EquipmentSlotType slot) {
        super(mod);
        this.name = name;
    }

    public TagAttributeModifier(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }


    public TagAttributeModifier(CompoundNBT nbt) {
        this(nbt.getString(NBTKeys.keys.attributeName()), AttributeModifier.load(nbt), EquipmentSlotType.byName(nbt.getString(NBTKeys.keys.attributeSlot())));
    }


    @Override
    public boolean isDefault() {
        return getNBT().isEmpty();
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = data.save();
        if (name != null && !name.isEmpty())
            nbt.putString(NBTKeys.keys.attributeDisplay(), name);
        if (slot != null)
            nbt.putInt(NBTKeys.keys.attributeSlot(), slot.ordinal());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return getNBT().getPrettyDisplay(space, indentation);
    }
}
