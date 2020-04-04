package creativeeditor.data.tag.entity;

import creativeeditor.data.Data;
import creativeeditor.data.base.DataRotation;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Rotations;
import net.minecraftforge.common.util.Constants.NBT;

public class TagEntityArmorStand extends TagEntity<ArmorStandEntity> {

    private @Getter Pose pose;

    public TagEntityArmorStand(CompoundNBT nbt) {
        pose = new Pose( nbt.getCompound( "Pose" ) );
    }


    @Override
    public ArmorStandEntity getData() {
        @SuppressWarnings( "resource" )
        ArmorStandEntity stand = new ArmorStandEntity( Minecraft.getInstance().world, 0, 0, 0 );
        pose.applyToArmorStand( stand );
        return stand;
    }


    @Override
    public boolean isDefault() {
        return pose.isDefault();
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put( "Pose", pose.getNBT() );
        return nbt;
    }
    
    public static class Pose implements Data<Pose, CompoundNBT>{
        private @Getter DataRotation head, body, leftArm, rightArm, leftLeg, rightLeg;
        
        private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
        private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
        private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
        private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
        private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
        private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
        
        public Pose(CompoundNBT nbt) {
            head = new DataRotation( nbt.getList( "Head", NBT.TAG_FLOAT ) );
            body = new DataRotation( nbt.getList( "Body", NBT.TAG_FLOAT ) );
            leftArm = new DataRotation( nbt.getList( "LeftArm", NBT.TAG_FLOAT ) );
            rightArm = new DataRotation( nbt.getList( "RightArm", NBT.TAG_FLOAT ) );
            leftLeg = new DataRotation( nbt.getList( "LeftLeg", NBT.TAG_FLOAT ) );
            rightLeg = new DataRotation( nbt.getList( "RightLeg", NBT.TAG_FLOAT ) );
        }

        @Override
        public Pose getData() {
            return this;
        }

        @Override
        public boolean isDefault() {
            return DEFAULT_HEAD_ROTATION.equals(head.getData()) && DEFAULT_BODY_ROTATION.equals(body.getData()) && DEFAULT_LEFTARM_ROTATION.equals(leftArm.getData()) && DEFAULT_RIGHTARM_ROTATION.equals(rightArm.getData()) && DEFAULT_LEFTLEG_ROTATION.equals(leftLeg.getData()) && DEFAULT_RIGHTLEG_ROTATION.equals(rightLeg.getData());
        }

        @Override
        public CompoundNBT getNBT() {
            CompoundNBT nbt = new CompoundNBT();
            if (!DEFAULT_HEAD_ROTATION.equals(head.getData())) {
                nbt.put("Head", head.getNBT());
            }

            if (!DEFAULT_BODY_ROTATION.equals(body.getData())) {
                nbt.put("Body", body.getNBT());
            }

            if (!DEFAULT_LEFTARM_ROTATION.equals(leftArm.getData())) {
                nbt.put("LeftArm", leftArm.getNBT());
            }

            if (!DEFAULT_RIGHTARM_ROTATION.equals(rightArm.getData())) {
                nbt.put("RightArm", rightArm.getNBT());
            }

            if (!DEFAULT_LEFTLEG_ROTATION.equals(leftLeg.getData())) {
                nbt.put("LeftLeg", leftLeg.getNBT());
            }

            if (!DEFAULT_RIGHTLEG_ROTATION.equals(rightLeg.getData())) {
                nbt.put("RightLeg", rightLeg.getNBT());
            }
            return nbt;
        }
        
        public void applyToArmorStand(ArmorStandEntity stand) {
            stand.setHeadRotation( head.getData() );
            stand.setBodyRotation( body.getData() );
            stand.setLeftArmRotation( leftArm.getData() );
            stand.setRightArmRotation( rightArm.getData() );
            stand.setLeftLegRotation( leftLeg.getData() );
            stand.setRightLegRotation( rightLeg.getData() );
        }
        
    }
}
