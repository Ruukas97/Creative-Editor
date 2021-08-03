package creativeeditor.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class EntityDrawUtils {

    private final ArmorStandEntity armorstand;
    private final DataItem item;

    public int addRotation = 0;
    private float rotation = 180.0F;


    public EntityDrawUtils(ArmorStandEntity arm, DataItem item) {
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


    public void drawArmorStand( ArmorStandEntity armorStand, int posX, int posY, int scale ) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef( (float) posX, (float) posY, 1050.0F );
        RenderSystem.scalef( 1.0F, 1.0F, -1.0F );
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate( 0.0D, 0.0D, 1000.0D );
        matrixstack.scale( (float) scale, (float) scale, (float) scale );
        Quaternion quaternion = Vector3f.ZP.rotationDegrees( 180.0F );
        matrixstack.mulPose(quaternion);
        float f2 = armorStand.yBodyRot;
        float f3 = armorStand.yRot;
        float f4 = armorStand.xRot;
        float f5 = armorStand.yHeadRotO;
        float f6 = armorStand.yHeadRot;
        if (addRotation > 0 || addRotation < 0) {
            rotation += 3 * (addRotation * -1);
        }
        armorStand.yBodyRot = rotation;
        armorStand.yRot = 180.0F;
        armorStand.yHeadRot = armorStand.yRot;
        armorStand.yHeadRotO = armorStand.yRot;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        entityrenderermanager.setRenderShadow( false );
        RenderHelper.setupForFlatItems();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();;
        entityrenderermanager.render( armorStand, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880 );
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow( true );
        armorStand.yBodyRot = f2;
        armorStand.yRot = f3;
        armorStand.xRot = f4;
        armorStand.yHeadRotO = f5;
        armorStand.yHeadRot = f6;
        RenderSystem.popMatrix();
    }
}
