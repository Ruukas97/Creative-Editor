package creativeeditor.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class HelperGui {

	public static final int GOOD_GREEN = 0x52b738;
	public static final int BAD_RED = 0xf44262;

	public static boolean isMouseInRegion(int mouseX, int mouseY, int xPos, int yPos, int width, int height) {
		return mouseX >= xPos && mouseY >= yPos && mouseX < xPos + width && mouseY < yPos + height;
	}

	public static void addTooltipTranslated(Button button, int mouseX, int mouseY, String str) {
		if (button != null && button.active && button.visible) {
			addTooltipTranslated(button.x, button.y, button.getWidth(), button.getHeight(), mouseX, mouseY, str);
		}
	}

	public static void addTooltip(Button button, int mouseX, int mouseY, String... str) {
		if (button != null && button.active && button.visible) {
			addToolTip(button.x, button.y, button.getWidth(), button.getHeight(), mouseX, mouseY, str);
		}
	}

	public static void addTooltipTranslated(int xPos, int yPos, int width, int height, int mouseX, int mouseY,
			String str) {
		List<String> strings = new ArrayList<>();

		for (int i = 1; i < 10; i++) {
			String s = (str + "." + i);
			if (I18n.hasKey(s)) {
				strings.add(I18n.format(s));
			} else {
				break;
			}
		}

		if (!strings.isEmpty()) {
			addToolTip(xPos, yPos, width, height, mouseX, mouseY, strings.toArray(new String[strings.size()]));
		} else {
			addToolTip(xPos, yPos, width, height, mouseX, mouseY, "missing localization: " + str);
		}
	}

	public static void addToolTip(int xPos, int yPos, int width, int height, int mouseX, int mouseY, String... str) {
		if (isMouseInRegion(mouseX, mouseY, xPos, yPos, width, height)) {
			if (str.length == 1) {
				getCurrentScreen().drawHoveringText(str[0], mouseX, mouseY);
			} else {
				List<String> strings = new ArrayList<>();

				for (String s : str) {
					strings.add(s);
				}

				getCurrentScreen().drawHoveringText(strings, mouseX, mouseY);
			}
		}
	}

	public static void dropStack(ItemStack stack) {
		if (!stack.isEmpty()) {
			Minecraft.getInstance().player.inventory.player.dropItem(stack, true);
			Minecraft.getInstance().playerController.sendPacketDropItem(stack);
		}
	}

	public static int getColorFromRGB(int alpha, int red, int green, int blue) {
		int color = alpha << 24;
		color += red << 16;
		color += green << 8;
		color += blue;
		return color;
	}

	public static void openWebLink(URI url) {
		try {
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop").invoke((Object) null);
			oclass.getMethod("browse", URI.class).invoke(object, url);
		} catch (Throwable throwable1) {
			Throwable throwable = throwable1.getCause();
		}
	}

	/**
	 * Draws an entity on the screen looking toward the cursor.
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, Entity ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) posX, (float) posY, 50.0F);
		GlStateManager.scalef((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotatef(180.0F, 0.1F, 0.0F, 1.0F);
		float f1 = ent.rotationYaw;
		GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F, 1.0F, 0.0F, 0.0F);
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 25.0F;
		// ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F;
		GlStateManager.translatef(0.0F, 0.0F, 0.0F);
		EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.rotationYaw = f1;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		// float f = ActiveRenderInfo.getRotationX();
		// float f11 = ActiveRenderInfo.getRotationZ();
		// float f2 = ActiveRenderInfo.getRotationYZ();
		// float f3 = ActiveRenderInfo.getRotationXY();
		// float f4 = ActiveRenderInfo.getRotationXZ();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(516, 0.003921569F);

	}

	/**
	 * Draws an entity on the screen looking toward the cursor.
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY,
			EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) posX, (float) posY, 50.0F);
		GlStateManager.scalef((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotatef(180.0F, 0.1F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 25.0F;
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 25.0F;
		// ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F;
		GlStateManager.translatef(0.0F, 0.0F, 0.0F);
		EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	/**
	 * Draws a banner facing the mouse cursor.
	 * 
	 * @param posX
	 * @param posY
	 * @param scale
	 * @param mouseX
	 * @param mouseY
	 * @param banner
	 */
	public static void renderBanner(int posX, int posY, int scale, float mouseX, float mouseY,
			TileEntityBanner banner) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) posX, (float) posY, 50.0F);
		GlStateManager.scalef((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
		// float f = ent.renderYawOffset;
		// float f1 = ent.rotationYaw;
		// float f2 = ent.rotationPitch;
		// float f3 = ent.prevRotationYawHead;
		// float f4 = ent.rotationYawHead;
		GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-((float) Math.atan((double) (mouseX / 40.0F))) * 20.5F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-((float) Math.atan((double) (mouseY / 40.0F))) * 2.0F, 1.0F, 0.0F, 0.0F);
		// ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) *
		// 20.0F;
		// ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
		// ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F;
		// ent.rotationYawHead = ent.rotationYaw;
		// ent.prevRotationYawHead = ent.rotationYaw;
		// GlStateManager.translate(0.0F, 0.0F, 0.0F);
		EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
		// rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		TileEntityRendererDispatcher.instance.render(banner, 0, 0, 0, Minecraft.getInstance().getRenderPartialTicks());
		rendermanager.setRenderShadow(true);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
