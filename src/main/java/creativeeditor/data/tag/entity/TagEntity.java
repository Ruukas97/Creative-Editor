package creativeeditor.data.tag.entity;

import creativeeditor.data.Data;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public abstract class TagEntity<E extends Entity> implements Data<E, CompoundNBT> {

}
