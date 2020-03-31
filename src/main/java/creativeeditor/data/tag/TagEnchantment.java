package creativeeditor.data.tag;

import creativeeditor.data.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.CompoundNBT;

@AllArgsConstructor
public class TagEnchantment implements Data<TagEnchantment, CompoundNBT> {
    private @Getter Enchantment enchantment;
    private @Getter int level;


    @Override
    public boolean isDefault() {
        return level == 0;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString( "id", enchantment.delegate.name().toString() );
        return nbt;
    }


    @Override
    public TagEnchantment getData() {
        return this;
    }
}
