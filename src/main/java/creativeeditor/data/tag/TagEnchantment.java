package creativeeditor.data.tag;

import creativeeditor.data.Data;
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
    int level;


    public TagEnchantment(INBT nbt) {
        this( nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT() );
    }


    public TagEnchantment(CompoundNBT nbt) {
        try {
            ResourceLocation rl = new ResourceLocation( nbt.getString( "id" ) );
            enchantment = GameRegistry.findRegistry( Enchantment.class ).getValue( rl );
        }
        catch (ResourceLocationException e) {
            enchantment = Enchantment.getEnchantmentByID( 0 );
        }

        level = nbt.getInt( "lvl" );
    }


    @Override
    public boolean isDefault() {
        return level == 0;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString( "id", enchantment.delegate.name().toString() );
        nbt.putInt( "lvl", level );
        return nbt;
    }


    @Override
    public TagEnchantment getData() {
        return this;
    }
}
