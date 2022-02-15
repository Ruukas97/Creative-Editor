package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataDouble;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.util.AttributeUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map.Entry;

public class TagAttributeModifier implements Data<TagAttributeModifier, CompoundTag> {
    /**
     * See: {@link }
     * {@link ItemStack#addAttributeModifier(Attribute, AttributeModifier, EquipmentSlot)}
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
    private final TagEnum<AttributeModifier.Operation> operation;

    @Getter
    private final TagEnum<EquipmentSlot> slot;

    public TagAttributeModifier(Entry<RegistryKey<Attribute>, Attribute> attributeRegistryEntry) {
        this(attributeRegistryEntry.getValue(), new AttributeModifier(attributeRegistryEntry.getKey().toString(), attributeRegistryEntry.getValue().getDefaultValue(), AttributeModifier.Operation.ADDITION), null);
    }

    public TagAttributeModifier(Attribute attribute, AttributeModifier modifier, EquipmentSlot slot) {
        this.attribute = attribute;
        this.name = new DataString(modifier.getName());
        this.amount = new DataDouble(modifier.getAmount());
        this.operation = new TagEnum<>(Operation.class, modifier.getOperation());
        this.slot = new TagEnum<>(EquipmentSlot.class, slot);
    }

    public TagAttributeModifier(Tag nbt) {
        this(nbt instanceof CompoundTag ? (CompoundTag) nbt : new CompoundTag());
    }

    public TagAttributeModifier(CompoundTag nbt) {
        this(AttributeUtils.getAttribute(nbt.getString(NBTKeys.keys.attributeName())), AttributeModifier.load(nbt), nbt.contains(NBTKeys.keys.attributeSlot(), Tag.TAG_STRING) ? EquipmentSlot.byName(nbt.getString(NBTKeys.keys.attributeSlot())) : EquipmentSlot.MAINHAND);
    }

    @Override
    public TagAttributeModifier getData() {
        return this;
    }

    @Override
    public boolean isDefault() {
        return this.attribute == null || amount.get() == 0 || getTag().isEmpty();
    }


    @Override
    public CompoundTag getTag() {
        if(attribute == null){
            return new CompoundTag();
        }
        CompoundTag nbt = createAttributeModifier().save();
        nbt.putString(NBTKeys.keys.attributeName(), AttributeUtils.getName(attribute));
        nbt.putString(NBTKeys.keys.attributeSlot(), slot.get().getName());
        return nbt;
    }

    public AttributeModifier createAttributeModifier(){
        return new AttributeModifier(name.get(), amount.get(), operation.get());
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return ((TextComponent) slot.getPrettyDisplay("", 0)).append(AttributeUtils.getText(getAttribute(), createAttributeModifier()));
//    }
}
