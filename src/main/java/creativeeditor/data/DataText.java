package creativeeditor.data;

import net.minecraft.nbt.CompoundNBT;

public class DataText implements Data<DataText, CompoundNBT> {

    @Override
    public DataText getData() {
        return this;
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
