package creativeeditor.data.tag;

import creativeeditor.data.base.DataRotation;
import lombok.Getter;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.nbt.CompoundNBT;

public class TagEntityArmorStand extends TagEntity<ArmorStandEntity> {

    private @Getter DataRotation head, body, leftArm, rightArm, leftLeg, rightLeg;


    public TagEntityArmorStand(CompoundNBT nbt) {
        
    }


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
