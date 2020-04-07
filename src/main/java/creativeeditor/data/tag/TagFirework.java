package creativeeditor.data.tag;

import creativeeditor.data.base.DataByte;
import creativeeditor.data.base.SingularData;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagFirework extends SingularData<TagList<TagExplosion>, CompoundNBT> {
    @Getter
    private final DataByte flight;


    public TagFirework(CompoundNBT nbt) {
        this( new TagList<>( nbt.getList( NBTKeys.keys.fireworksExplosions(), NBT.TAG_COMPOUND ) ), nbt.getByte( NBTKeys.keys.fireworksFlight() ) );
    }


    public TagFirework(TagList<TagExplosion> list, byte flight) {
        super( list );
        this.flight = new DataByte( flight );
    }


    @Override
    public boolean isDefault() {
        return data.isDefault();
    }


    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        nbt.put( keys.fireworksFlight(), flight.getNBT() );
        nbt.put( keys.fireworksExplosions(), data.getNBT() );
        return nbt;
    }
}
