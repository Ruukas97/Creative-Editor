package infinityitemeditor.util;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.decoration.ArmorStand;

public class EntityDrawUtils {

    private final ArmorStand armorstand;
    private DataItem item;

    public int addRotation = 0;
    private float rotation = 180.0F;


    public EntityDrawUtils(DataItem item) {
        this.armorstand = new ArmorStand(Minecraft.getInstance().level, 0, 0, 0);
        this.item = item;
    }


    public void updateArmorStand() {
        if (armorstand != null) {
            armorstand.readAdditionalSaveData(getStandData().getTag());
        }
    }


    private TagEntityArmorStand getStandData() {
        return item.getTag().getArmorStandTag();
    }


    public void drawArmorStand(int posX, int posY, int scale) {
//        RenderSystem.pushMatrix();
//        RenderSystem.translatef((float) posX, (float) posY, 1050.0F);
//        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
//        PoseStack poseStack = new PoseStack();
//        matrixstack.translate(0.0D, 0.0D, 1000.0D);
//        matrixstack.scale((float) scale, (float) scale, (float) scale);
//        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
//        matrixstack.mulPose(quaternion);
//        float f2 = armorstand.yBodyRot;
//        float f3 = armorstand.yRot;
//        float f4 = armorstand.xRot;
//        float f5 = armorstand.yHeadRotO;
//        float f6 = armorstand.yHeadRot;
//        if (addRotation > 0 || addRotation < 0) {
//            rotation += 3 * (addRotation * -1);
//        }
//        armorstand.yBodyRot = rotation;
//        armorstand.yRot = 180.0F;
//        armorstand.yHeadRot = armorstand.yRot;
//        armorstand.yHeadRotO = armorstand.yRot;
//        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
//        entityrenderermanager.setRenderShadow(false);
//        RenderHelper.setupForFlatItems();
//        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
//        ;
//        entityrenderermanager.render(armorstand, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
//        irendertypebuffer$impl.endBatch();
//        entityrenderermanager.setRenderShadow(true);
//        armorstand.yBodyRot = f2;
//        armorstand.yRot = f3;
//        armorstand.xRot = f4;
//        armorstand.yHeadRotO = f5;
//        armorstand.yHeadRot = f6;
//        RenderSystem.popMatrix();
    }
}
