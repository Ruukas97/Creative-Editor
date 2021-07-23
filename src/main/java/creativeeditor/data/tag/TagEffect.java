package creativeeditor.data.tag;

import creativeeditor.data.base.SingularData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;

public class TagEffect extends SingularData<EffectInstance, CompoundNBT> {
    /*
     * See: {@link Potion} {@link Potions} {@link Effect} {@link EffectInstance}
     */
    
    public TagEffect(INBT nbt) {
        this( nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT() );
    }

    public TagEffect(CompoundNBT nbt) {
        this( EffectInstance.load( nbt ) );
    }


    public TagEffect(EffectInstance data) {
        super( data );
    }


    @Override
    public boolean isDefault() {
        return data.getDuration() == 0;
    }


    @Override
    public CompoundNBT getNBT() {
        return data.save( new CompoundNBT() );
    }
}
