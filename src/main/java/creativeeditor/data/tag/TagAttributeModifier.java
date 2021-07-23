package creativeeditor.data.tag;

import javax.annotation.Nullable;

import creativeeditor.data.base.SingularData;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.world.storage.loot.functions.SetAttributes;

public class TagAttributeModifier extends SingularData<AttributeModifier, CompoundNBT> {
    /**
     * See: {@link SetAttributes}
     * {@link ItemStack#addAttributeModifier(String, AttributeModifier, EquipmentSlotType)}
     * {@link SharedMonsterAttributes}
     */

    @Getter
    @Setter
    private String name;

    @Nullable
    @Getter
    @Setter
    private EquipmentSlotType slot;


    public TagAttributeModifier(String name, AttributeModifier mod, EquipmentSlotType slot) {
        super( mod );
    }
    
    public TagAttributeModifier(INBT nbt) {
        this( nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT() );
    }


    public TagAttributeModifier(CompoundNBT nbt) {
        this( nbt.getString( NBTKeys.keys.attributeName() ), SharedMonsterAttributes.readAttributeModifier( nbt ), EquipmentSlotType.byName( nbt.getString( NBTKeys.keys.attributeSlot() ) ) );
    }


    @Override
    public boolean isDefault() {
        return getNBT().isEmpty();
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = SharedMonsterAttributes.writeAttributeModifier( data );
        if (name != null && !name.isEmpty())
            nbt.putString( NBTKeys.keys.attributeDisplay(), name );
        if (slot != null)
            nbt.putInt( NBTKeys.keys.attributeSlot(), slot.ordinal() );
        return nbt;
    }
}
