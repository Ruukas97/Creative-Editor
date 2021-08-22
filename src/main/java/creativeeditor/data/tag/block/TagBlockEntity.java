package creativeeditor.data.tag.block;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagDisplay;
import net.minecraft.nbt.CompoundNBT;

public class TagBlockEntity implements Data<TagDisplay, CompoundNBT> {


    public TagBlockEntity(DataItem item, CompoundNBT compound) {

    }

    @Override
    public TagDisplay getData() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public CompoundNBT getNBT() {
        return null;
    }
}
