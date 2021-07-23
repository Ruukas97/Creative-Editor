package creativeeditor.data.tag.entity;

import creativeeditor.data.Data;
import creativeeditor.data.base.DataBitField;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataRotation;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Rotations;
import net.minecraftforge.common.util.Constants.NBT;

public class TagEntityArmorStand extends TagEntity<ArmorStandEntity> {

    private @Getter
    final
    DataBoolean marker;
    private @Getter
    final
    DataBoolean invisible;
    private @Getter
    final
    DataBoolean noBasePlate;
    private @Getter
    final
    DataBoolean noGravity;
    private @Getter
    final
    DataBoolean showArms;
    private @Getter
    final
    DataBoolean small;
    private @Getter
    final
    Pose pose;
    private @Getter
    final
    DataBitField disabledSlots;


    public TagEntityArmorStand(CompoundNBT nbt) {
        marker = new DataBoolean( nbt.getBoolean( "Marker" ) );
        invisible = new DataBoolean( nbt.getBoolean( "Invisible" ) );
        noBasePlate = new DataBoolean( nbt.getBoolean( "NoBasePlate" ) );
        showArms = new DataBoolean( nbt.getBoolean( "ShowArms" ) );
        small = new DataBoolean( nbt.getBoolean( "Small" ) );
        pose = new Pose( nbt.getCompound( "Pose" ) );
        noGravity = new DataBoolean(nbt.getBoolean("NoGravity"));
        disabledSlots = new DataBitField( 21, nbt.getInt( "DisabledSlots" ) );
    }


    @Override
    public ArmorStandEntity getData() {
        Minecraft mc = Minecraft.getInstance();
        ArmorStandEntity stand = new ArmorStandEntity( mc.level, 0, 0, 0 );
        pose.applyToArmorStand( stand );
        return stand;
    }


    @Override
    public boolean isDefault() {
        return noGravity.isDefault() && marker.isDefault() && invisible.isDefault() && noBasePlate.isDefault() && showArms.isDefault() && small.isDefault() && pose.isDefault() && disabledSlots.isDefault();
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if (!marker.isDefault())
            nbt.put( "Marker", marker.getNBT() );
        if (!invisible.isDefault())
            nbt.put( "Invisible", invisible.getNBT() );
        if (!noBasePlate.isDefault())
            nbt.put( "NoBasePlate", noBasePlate.getNBT() );
        if (!showArms.isDefault())
            nbt.put( "ShowArms", showArms.getNBT() );
        if (!small.isDefault())
            nbt.put( "Small", small.getNBT() );
        if (!noGravity.isDefault())
            nbt.put( "NoGravity", noGravity.getNBT() );
        if (!pose.isDefault())
            nbt.put( "Pose", pose.getNBT() );
        if (!disabledSlots.isDefault())
            nbt.put( "DisabledSlots", disabledSlots.getNBT() );
        return nbt;
    }


    public static class Pose implements Data<Pose, CompoundNBT> {
        private @Getter
        DataRotation head, body, leftArm, rightArm, leftLeg, rightLeg;

        private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations( 0.0F, 0.0F, 0.0F );
        private static final Rotations DEFAULT_BODY_ROTATION = new Rotations( 0.0F, 0.0F, 0.0F );
        private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations( 350.0F, 0.0F, 350.0F );
        private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations( 345.0F, 0.0F, 10.0F );
        private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations( 359.0F, 0.0F, 359.0F );
        private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations( 1.0F, 0.0F, 1.0F );


        public Pose(CompoundNBT nbt) {
            head = getOrDefault( nbt, "Head", DEFAULT_HEAD_ROTATION );
            body = getOrDefault( nbt, "Body", DEFAULT_BODY_ROTATION );
            leftArm = getOrDefault( nbt, "LeftArm", DEFAULT_LEFTARM_ROTATION );
            rightArm = getOrDefault( nbt, "RightArm", DEFAULT_RIGHTARM_ROTATION );
            leftLeg = getOrDefault( nbt, "LeftLeg", DEFAULT_LEFTLEG_ROTATION );
            rightLeg = getOrDefault( nbt, "RightLeg", DEFAULT_RIGHTLEG_ROTATION );
        }


        public DataRotation getOrDefault( CompoundNBT nbt, String key, Rotations def ) {
            if (nbt.contains( key, NBT.TAG_LIST )) {
                return new DataRotation( nbt.getList( key, NBT.TAG_FLOAT ) );
            }
            return new DataRotation( def );
        }


        @Override
        public Pose getData() {
            return this;
        }


        @Override
        public boolean isDefault() {
            return DEFAULT_HEAD_ROTATION.equals( head.getData() ) && DEFAULT_BODY_ROTATION.equals( body.getData() ) && DEFAULT_LEFTARM_ROTATION.equals( leftArm.getData() ) && DEFAULT_RIGHTARM_ROTATION.equals( rightArm.getData() ) && DEFAULT_LEFTLEG_ROTATION.equals( leftLeg.getData() ) && DEFAULT_RIGHTLEG_ROTATION.equals( rightLeg.getData() );
        }


        @Override
        public CompoundNBT getNBT() {
            CompoundNBT nbt = new CompoundNBT();
            if (!DEFAULT_HEAD_ROTATION.equals( head.getData() )) {
                nbt.put( "Head", head.getNBT() );
            }

            if (!DEFAULT_BODY_ROTATION.equals( body.getData() )) {
                nbt.put( "Body", body.getNBT() );
            }

            if (!DEFAULT_LEFTARM_ROTATION.equals( leftArm.getData() )) {
                nbt.put( "LeftArm", leftArm.getNBT() );
            }

            if (!DEFAULT_RIGHTARM_ROTATION.equals( rightArm.getData() )) {
                nbt.put( "RightArm", rightArm.getNBT() );
            }

            if (!DEFAULT_LEFTLEG_ROTATION.equals( leftLeg.getData() )) {
                nbt.put( "LeftLeg", leftLeg.getNBT() );
            }

            if (!DEFAULT_RIGHTLEG_ROTATION.equals( rightLeg.getData() )) {
                nbt.put( "RightLeg", rightLeg.getNBT() );
            }
            return nbt;
        }


        public void reset() {
            head = new DataRotation( DEFAULT_HEAD_ROTATION );
            body = new DataRotation( DEFAULT_BODY_ROTATION );
            rightArm = new DataRotation( DEFAULT_RIGHTARM_ROTATION );
            leftArm = new DataRotation( DEFAULT_LEFTARM_ROTATION );
            rightLeg = new DataRotation( DEFAULT_RIGHTLEG_ROTATION );
            leftLeg = new DataRotation( DEFAULT_LEFTLEG_ROTATION );
        }


        public void applyToArmorStand( ArmorStandEntity stand ) {
            stand.setHeadPose( head.getData() );
            stand.setBodyPose( body.getData() );
            stand.setLeftArmPose( leftArm.getData() );
            stand.setRightArmPose( rightArm.getData() );
            stand.setLeftLegPose( leftLeg.getData() );
            stand.setRightLegPose( rightLeg.getData() );
        }

    }
}
