package creativeeditor.data.tag;

import creativeeditor.data.NumberRange;
import lombok.Getter;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class TagEntityArmorStand extends TagEntity<ArmorStandEntity> {

    private @Getter NumberRange headX, headY, headZ;


    @Override
    public ArmorStandEntity getData() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean isDefault() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public CompoundNBT getNBT() {
        // TODO Auto-generated method stub
        return null;
    }

}
