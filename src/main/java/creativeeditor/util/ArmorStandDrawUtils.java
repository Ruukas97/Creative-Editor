package creativeeditor.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ArmorStandEntity;

public class ArmorStandDrawUtils {

	private ArmorStandEntity armorstand;
	private DataItem item;
	
	
	public ArmorStandDrawUtils(ArmorStandEntity arm, DataItem item) {
		this.armorstand = arm;
		this.item = item;
	}
	
	
	public void updateArmorStand() {
		if (armorstand != null) {
			armorstand.readAdditional(getStandData().getNBT());
		}
	}
	
	public  TagEntityArmorStand getStandData() {
		return item.getTag().getArmorStandTag();
	}
	
	public void drawArmorStand(ArmorStandEntity ent, int posX, int posY, int scale) {

		GlStateManager.pushMatrix();
		GlStateManager.color3f(1f, 1f, 1f);
		GlStateManager.enableColorMaterial();
		GlStateManager.translatef((float) posX, (float) posY, 50.0F);
		GlStateManager.scalef((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);

		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotatef(40.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotatef(-50.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(10F, 1.0F, 0.0F, 0.0F);
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translatef(0.0F, 0.0F, 0.0F);
		EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		GL11.glPopAttrib();
		ent.renderYawOffset = f + 1;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableTexture();
		GlStateManager.popMatrix();
	}
}
