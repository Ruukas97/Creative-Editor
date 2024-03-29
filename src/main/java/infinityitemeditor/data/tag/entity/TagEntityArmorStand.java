package infinityitemeditor.data.tag.entity;

import com.google.common.base.Strings;
import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataBitField;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.base.DataMap;
import infinityitemeditor.data.base.DataRotation;
import infinityitemeditor.data.tag.TagItemList;
import infinityitemeditor.render.NBTIcons;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class TagEntityArmorStand extends TagEntity<ArmorStandEntity> {

    @Getter
    private final DataBoolean marker;
    @Getter
    private final DataBoolean invisible;
    @Getter
    private final DataBoolean noBasePlate;
    @Getter
    private final DataBoolean noGravity;
    @Getter
    private final DataBoolean showArms;
    @Getter
    private final DataBoolean small;
    @Getter
    private final Pose pose;
    @Getter
    private final DataBitField disabledSlots;


    public TagEntityArmorStand(CompoundNBT nbt) {
        super(new TagItemList(nbt.getList("ArmorItems", NBT.TAG_COMPOUND), 4), new TagItemList(nbt.getList("HandItems", NBT.TAG_COMPOUND), 2));
        marker = new DataBoolean(nbt.getBoolean("Marker"));
        invisible = new DataBoolean(nbt.getBoolean("Invisible"));
        noBasePlate = new DataBoolean(nbt.getBoolean("NoBasePlate"));
        showArms = new DataBoolean(nbt.getBoolean("ShowArms"));
        small = new DataBoolean(nbt.getBoolean("Small"));
        pose = new Pose(nbt.getCompound("Pose"));
        noGravity = new DataBoolean(nbt.getBoolean("NoGravity"));
        disabledSlots = new DataBitField(21, nbt.getInt("DisabledSlots"));
    }


    @Override
    public ArmorStandEntity getData() {
        Minecraft mc = Minecraft.getInstance();
        ArmorStandEntity stand = new ArmorStandEntity(EntityType.ARMOR_STAND, mc.level);
        stand.readAdditionalSaveData(getNBT());
        return stand;
    }


    @Override
    public boolean isDefault() {
        return noGravity.isDefault() && marker.isDefault() && invisible.isDefault() && noBasePlate.isDefault() && showArms.isDefault() && small.isDefault() && pose.isDefault() && disabledSlots.isDefault() && super.isDefault();
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if (!marker.isDefault())
            nbt.put("Marker", marker.getNBT());
        if (!invisible.isDefault())
            nbt.put("Invisible", invisible.getNBT());
        if (!noBasePlate.isDefault())
            nbt.put("NoBasePlate", noBasePlate.getNBT());
        if (!showArms.isDefault())
            nbt.put("ShowArms", showArms.getNBT());
        if (!small.isDefault())
            nbt.put("Small", small.getNBT());
        if (!noGravity.isDefault())
            nbt.put("NoGravity", noGravity.getNBT());
        if (!pose.isDefault())
            nbt.put("Pose", pose.getNBT());
        if (!disabledSlots.isDefault())
            nbt.put("DisabledSlots", disabledSlots.getNBT());
        if (!super.isDefault()) {
            CompoundNBT sup = super.getNBT();
            nbt.merge(sup);
        }
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");

        if (!space.isEmpty()) {
            iformattabletextcomponent.append("\n");
        }

        IFormattableTextComponent iformattabletextcomponent1;
        String marker = "Marker";
        String invisible = "Invisible";
        String noBasePlate = "NoBasePlate";
        String showArmsString = "ShowArms";
        String small = "Small";
        String noGravity = "NoGravity";
        String pose = "Pose";
        String disabledSlots = "DisabledSlots";
        String armorItems = "ArmorItems";
        String handItems = "HandItems";

        boolean showMarker = !getMarker().isDefault();
        boolean showInvisible = !getInvisible().isDefault();
        boolean showBasePlate = !getNoBasePlate().isDefault();
        boolean showArms = !getArmorItems().isDefault();
        boolean showSmall = !getSmall().isDefault();
        boolean showGravity = !getNoGravity().isDefault();
        boolean showPose = !getPose().isDefault();
        boolean showDisabledSlots = !getDisabledSlots().isDefault();
        boolean showArmorItems = !getArmorItems().isDefault();
        boolean showHandItems = !getHandItems().isDefault();


        if (showMarker) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(marker)).append(String.valueOf(':')).append(" ").append(getMarker().getPrettyDisplay(space, indentation + 1));
            if (showInvisible || showBasePlate || showArms || showSmall || showGravity || showPose || showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showInvisible) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(invisible)).append(String.valueOf(':')).append(" ").append(getInvisible().getPrettyDisplay(space, indentation + 1));
            if (showBasePlate || showArms || showSmall || showGravity || showPose || showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showBasePlate) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(noBasePlate)).append(String.valueOf(':')).append(" ").append(getNoBasePlate().getPrettyDisplay(space, indentation + 1));
            if (showArms || showSmall || showGravity || showPose || showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showArms) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(showArmsString)).append(String.valueOf(':')).append(" ").append(getShowArms().getPrettyDisplay(space, indentation + 1));
            if (showSmall || showGravity || showPose || showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showSmall) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(small)).append(String.valueOf(':')).append(" ").append(getSmall().getPrettyDisplay(space, indentation + 1));
            if (showGravity || showPose || showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showGravity) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(noGravity)).append(String.valueOf(':')).append(" ").append(getNoGravity().getPrettyDisplay(space, indentation + 1));
            if (showPose || showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showPose) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(pose)).append(String.valueOf(':')).append(" ").append(getPose().getPrettyDisplay(space, indentation + 1));
            if (showDisabledSlots || showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showDisabledSlots) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(disabledSlots)).append(String.valueOf(':')).append(" ").append(getDisabledSlots().getPrettyDisplay(space, indentation + 1));
            if (showArmorItems || showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showArmorItems) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(armorItems)).append(String.valueOf(':')).append(" ").append(getArmorItems().getPrettyDisplay(space, indentation + 1));
            if (showHandItems)
                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (showHandItems) {
            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(handItems)).append(String.valueOf(':')).append(" ").append(getHandItems().getPrettyDisplay(space, indentation + 1));
            iformattabletextcomponent.append(iformattabletextcomponent1);
        }

        if (!space.isEmpty()) {
            iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
        }

        iformattabletextcomponent.append("}");
        return iformattabletextcomponent;
    }


    public static class Pose implements Data<Pose, CompoundNBT> {
        @Getter
        @Setter
        protected Data<?, ?> parent;

        private @Getter
        DataRotation head, body, leftArm, rightArm, leftLeg, rightLeg;

        private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
        private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
        private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(350.0F, 0.0F, 350.0F);
        private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(345.0F, 0.0F, 10.0F);
        private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(359.0F, 0.0F, 359.0F);
        private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);


        public Pose(CompoundNBT nbt) {
            head = getOrDefault(nbt, "Head", DEFAULT_HEAD_ROTATION);
            body = getOrDefault(nbt, "Body", DEFAULT_BODY_ROTATION);
            leftArm = getOrDefault(nbt, "LeftArm", DEFAULT_LEFTARM_ROTATION);
            rightArm = getOrDefault(nbt, "RightArm", DEFAULT_RIGHTARM_ROTATION);
            leftLeg = getOrDefault(nbt, "LeftLeg", DEFAULT_LEFTLEG_ROTATION);
            rightLeg = getOrDefault(nbt, "RightLeg", DEFAULT_RIGHTLEG_ROTATION);
        }


        public DataRotation getOrDefault(CompoundNBT nbt, String key, Rotations def) {
            if (nbt.contains(key, NBT.TAG_LIST)) {
                return new DataRotation(nbt.getList(key, NBT.TAG_FLOAT));
            }
            return new DataRotation(def);
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

        @Override
        public ITextComponent getPrettyDisplay(String space, int indentation) {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;
            String head = "Head";
            String body = "Body";
            String leftArm = "LeftArm";
            String rightArm = "RightArm";
            String leftLeg = "LeftLeg";
            String rightLeg = "RightLeg";

            boolean showHead = !getHead().isDefault();
            boolean showBody = !getBody().isDefault();
            boolean showLeftArm = !getLeftArm().isDefault();
            boolean showRightArm = !getRightArm().isDefault();
            boolean showLeftLeg = !getLeftLeg().isDefault();
            boolean showRightLeg = !getRightLeg().isDefault();

            if (showHead) {
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(head)).append(String.valueOf(':')).append(" ").append(getHead().getPrettyDisplay(space, indentation + 1));
                if (showBody || showLeftArm || showRightArm || showLeftLeg || showRightLeg)
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (showBody) {
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(body)).append(String.valueOf(':')).append(" ").append(getBody().getPrettyDisplay(space, indentation + 1));
                if (showLeftArm || showRightArm || showLeftLeg || showRightLeg)
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (showLeftArm) {
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(leftArm)).append(String.valueOf(':')).append(" ").append(getLeftArm().getPrettyDisplay(space, indentation + 1));
                if (showRightArm || showLeftLeg || showRightLeg)
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (showRightArm) {
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(rightArm)).append(String.valueOf(':')).append(" ").append(getRightArm().getPrettyDisplay(space, indentation + 1));
                if (showLeftLeg || showRightLeg)
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (showLeftLeg) {
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(leftLeg)).append(String.valueOf(':')).append(" ").append(getLeftLeg().getPrettyDisplay(space, indentation + 1));
                if (showRightLeg)
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (showRightLeg) {
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(rightLeg)).append(String.valueOf(':')).append(" ").append(getRightLeg().getPrettyDisplay(space, indentation + 1));
                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
            }

            iformattabletextcomponent.append("}");
            return iformattabletextcomponent;
        }


        public void reset() {
            head = new DataRotation(DEFAULT_HEAD_ROTATION);
            body = new DataRotation(DEFAULT_BODY_ROTATION);
            rightArm = new DataRotation(DEFAULT_RIGHTARM_ROTATION);
            leftArm = new DataRotation(DEFAULT_LEFTARM_ROTATION);
            rightLeg = new DataRotation(DEFAULT_RIGHTLEG_ROTATION);
            leftLeg = new DataRotation(DEFAULT_LEFTLEG_ROTATION);
        }


        public void applyToArmorStand(ArmorStandEntity stand) {
            stand.setHeadPose(head.getData());
            stand.setBodyPose(body.getData());
            stand.setLeftArmPose(leftArm.getData());
            stand.setRightArmPose(rightArm.getData());
            stand.setLeftLegPose(leftLeg.getData());
            stand.setRightLegPose(rightLeg.getData());
        }

        @Override
        public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
            NBTIcons.COMPOUND_TAG.renderIcon(mc, matrix, x, y);
        }
    }
}
