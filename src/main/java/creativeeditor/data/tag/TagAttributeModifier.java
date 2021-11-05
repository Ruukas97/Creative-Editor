package creativeeditor.data.tag;

import creativeeditor.data.Data;
import creativeeditor.data.base.DataDouble;
import creativeeditor.data.base.DataString;
import creativeeditor.data.version.NBTKeys;
import creativeeditor.util.AttributeUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;

import java.util.Map.Entry;

public class TagAttributeModifier implements Data<TagAttributeModifier, CompoundNBT> {
    /**
     * See: {@link }
     * {@link ItemStack#addAttributeModifier(Attribute, AttributeModifier, EquipmentSlotType)}
     * {@link }
     */

    @Getter
    @Setter
    private Attribute attribute;

    @Getter
    @Setter
    private final DataString name;

    @Getter
    @Setter
    private final DataDouble amount;

    @Getter
    @Setter
    private Operation operation;

    @Getter
    @Setter
    private EquipmentSlotType slot;

    public TagAttributeModifier(Entry<RegistryKey<Attribute>, Attribute> attributeRegistryEntry) {
        this(attributeRegistryEntry.getValue(), new AttributeModifier(attributeRegistryEntry.getKey().toString(), attributeRegistryEntry.getValue().getDefaultValue(), AttributeModifier.Operation.ADDITION), null);
    }

    public TagAttributeModifier(Attribute attribute, AttributeModifier modifier, EquipmentSlotType slot) {
        this.attribute = attribute;
        this.name = new DataString(modifier.getName());
        this.amount = new DataDouble(modifier.getAmount());
        this.operation = modifier.getOperation();
        this.slot = slot;
    }

    public TagAttributeModifier(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }

    public TagAttributeModifier(CompoundNBT nbt) {
        this(AttributeUtils.getAttribute(nbt.getString(NBTKeys.keys.attributeName())), AttributeModifier.load(nbt), EquipmentSlotType.byName(nbt.getString(NBTKeys.keys.attributeSlot())));
    }

    @Override
    public TagAttributeModifier getData() {
        return this;
    }

    @Override
    public boolean isDefault() {
        return this.attribute == null || amount.get() == 0 || getNBT().isEmpty();
    }


    @Override
    public CompoundNBT getNBT() {
        if(attribute == null){
            return new CompoundNBT();
        }
        CompoundNBT nbt = new AttributeModifier(name.get(), amount.get(), operation).save();
        nbt.putString(NBTKeys.keys.attributeName(), AttributeUtils.getName(attribute));
        if (slot != null)
            nbt.putInt(NBTKeys.keys.attributeSlot(), slot.ordinal());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return getNBT().getPrettyDisplay(space, indentation);
    }
}
