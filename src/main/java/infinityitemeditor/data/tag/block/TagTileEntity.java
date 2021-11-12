package infinityitemeditor.data.tag.block;

import infinityitemeditor.data.Data;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public abstract class TagTileEntity<E extends TileEntity> implements Data<E, CompoundNBT> {

}
