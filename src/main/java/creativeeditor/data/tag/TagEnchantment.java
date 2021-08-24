package creativeeditor.data.tag;

import creativeeditor.data.Data;
import creativeeditor.data.NumberRangeInt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraftforge.fml.common.registry.GameRegistry;

@AllArgsConstructor
public class TagEnchantment implements Data<TagEnchantment, CompoundNBT> {
    private @Getter
    Enchantment enchantment;
    private @Getter
    NumberRangeInt level;


    public TagEnchantment(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }


    public TagEnchantment(CompoundNBT nbt) {
        try {
            ResourceLocation rl = new ResourceLocation(nbt.getString("id"));
            enchantment = GameRegistry.findRegistry(Enchantment.class).getValue(rl);
        } catch (ResourceLocationException e) {
            enchantment = Enchantment.byId(0);
        }

        level = new NumberRangeInt(nbt.getInt( "lvl" ), 1, Integer.MAX_VALUE);
    }

    public TagEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = new NumberRangeInt(level, 1, Integer.MAX_VALUE);
    }


        @Override
    public boolean isDefault() {
        return level.get() == 0;
    }

    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString( "id", enchantment.delegate.name().toString() );
        nbt.putInt( "lvl", level.get() );
        return nbt;
    }

    @Override
    public TagEnchantment getData() {
        return this;
    }
}
