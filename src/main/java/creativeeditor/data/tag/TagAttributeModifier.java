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
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.common.util.Constants;

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
    private final DataString name;

    @Getter
    private final DataDouble amount;

    @Getter
    private final TagEnum<Operation> operation;

    @Getter
    private final TagEnum<EquipmentSlotType> slot;

    public TagAttributeModifier(Entry<RegistryKey<Attribute>, Attribute> attributeRegistryEntry) {
        this(attributeRegistryEntry.getValue(), new AttributeModifier(attributeRegistryEntry.getKey().toString(), attributeRegistryEntry.getValue().getDefaultValue(), AttributeModifier.Operation.ADDITION), null);
    }

    public TagAttributeModifier(Attribute attribute, AttributeModifier modifier, EquipmentSlotType slot) {
        this.attribute = attribute;
        this.name = new DataString(modifier.getName());
        this.amount = new DataDouble(modifier.getAmount());
        this.operation = new TagEnum<>(Operation.class, modifier.getOperation());
        this.slot = new TagEnum<>(EquipmentSlotType.class, slot);
    }

    public TagAttributeModifier(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }

    public TagAttributeModifier(CompoundNBT nbt) {
        this(AttributeUtils.getAttribute(nbt.getString(NBTKeys.keys.attributeName())), AttributeModifier.load(nbt), nbt.contains(NBTKeys.keys.attributeSlot(), Constants.NBT.TAG_STRING) ? EquipmentSlotType.byName(nbt.getString(NBTKeys.keys.attributeSlot())) : EquipmentSlotType.MAINHAND);
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
        CompoundNBT nbt = createAttributeModifier().save();
        nbt.putString(NBTKeys.keys.attributeName(), AttributeUtils.getName(attribute));
        nbt.putInt(NBTKeys.keys.attributeSlot(), slot.get().ordinal());
        return nbt;
    }

    public AttributeModifier createAttributeModifier(){
        return new AttributeModifier(name.get(), amount.get(), operation.get());
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return ((TextComponent) slot.getPrettyDisplay("", 0)).append(AttributeUtils.getText(getAttribute(), createAttributeModifier()));
    }
}
