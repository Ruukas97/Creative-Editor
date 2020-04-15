package creativeeditor.data.tag;

import creativeeditor.data.Data;
import creativeeditor.data.base.DataDouble;
import creativeeditor.data.base.DataString;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.world.storage.MapDecoration;

public class TagMapDecoration implements Data<TagMapDecoration, CompoundNBT> {

    @Getter
    private final DataString id;
    @Getter
    @Setter
    private MapDecoration.Type type;
    @Getter
    private final DataDouble x;
    @Getter
    private final DataDouble y;
    @Getter
    private final DataDouble rotation;

    public TagMapDecoration(INBT nbt) {
        this( nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT() );
    }

    public TagMapDecoration(CompoundNBT nbt) {
        NBTKeys keys = NBTKeys.keys;
        id = new DataString( nbt.getString( keys.decorationId() ) );
        type = MapDecoration.Type.byIcon( nbt.getByte( keys.decorationType() ) );
        x = new DataDouble( nbt.getDouble( "x" ) );
        y = new DataDouble( nbt.getDouble( "y" ) );
        rotation = new DataDouble( nbt.getDouble( keys.decorationRotation() ) );
    }


    @Override
    public TagMapDecoration getData() {
        return null;
    }


    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        NBTKeys keys = NBTKeys.keys;
        nbt.put( keys.decorationId(), id.getNBT() );
        nbt.put( keys.decorationType(), new ByteNBT( type.getIcon() ) );
        nbt.put( "x", x.getNBT() );
        nbt.put( "y", y.getNBT() );
        nbt.put( keys.decorationRotation(), rotation.getNBT() );
        return nbt;
    }
}
