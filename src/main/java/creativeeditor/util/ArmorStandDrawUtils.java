package creativeeditor.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class ArmorStandDrawUtils {

    private final ArmorStandEntity armorstand;
    private final DataItem item;

    public int addRotation = 0;
    public boolean isDragging = false;


    public ArmorStandDrawUtils(ArmorStandEntity arm, DataItem item) {
        this.armorstand = arm;
        this.item = item;
    }


    public void updateArmorStand() {
        if (armorstand != null) {
            armorstand.readAdditionalSaveData(getStandData().getNBT());
        }
    }


    public TagEntityArmorStand getStandData() {
        return item.getTag().getArmorStandTag();
    }


    public void drawArmorStand(ArmorStandEntity armorStand, int posX, int posY, int scale) {
        //float f = (float) Math.atan( (double) (mouseX / 40.0F) );
        //float f1 = (float) Math.atan( (double) (mouseY / 40.0F) );
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float) posX, (float) posY, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float) scale, (float) scale, (float) scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        //Quaternion quaternion1 = Vector3f.XP.rotationDegrees( f1 * 20.0F );
        //quaternion.multiply( quaternion1 );
        matrixstack.translate(quaternion.i(), quaternion.j(), quaternion.k());
        float f2 = armorStand.yBodyRotO;
        float f3 = armorStand.yBodyRot;
        float f4 = armorStand.xRot;
        float f5 = armorStand.yHeadRotO;
        float f6 = armorStand.yHeadRot;
        int add = 0;
        if (isDragging) {
            add = 2 * (addRotation * -1);
        } else {
            add = 1;
        }
        armorStand.yBodyRotO = 180.0F + add;
        armorStand.yBodyRot = 180.0F;
        armorStand.yHeadRotO = armorStand.yRotO;
        armorStand.yHeadRot = armorStand.yRot;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        //quaternion1.conjugate();
        //entityrenderermanager.setCameraOrientation( quaternion1 );
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        entityrenderermanager.render(armorStand, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
        armorStand.yBodyRotO = f2;
        armorStand.yBodyRot = f3;
        armorStand.xRot = f4;
        armorStand.yHeadRotO = f5;
        armorStand.yHeadRot = f6;
        RenderSystem.popMatrix();
    }
}
