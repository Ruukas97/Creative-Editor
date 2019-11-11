package creativeeditor.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class CameraUtil {
	public static void cyclePerspective() {
		Minecraft mc = Minecraft.getInstance();
		mc.gameSettings.thirdPersonView++;
		mc.gameSettings.thirdPersonView %= 3;

		if (mc.gameSettings.thirdPersonView == 0) {
			mc.gameRenderer.loadEntityShader(mc.getRenderViewEntity());
		} else if (mc.gameSettings.thirdPersonView == 1) {
			mc.gameRenderer.loadEntityShader((Entity) null);
		}
	}
	
	public static void setThirdPersonPerspective() {
		Minecraft mc = Minecraft.getInstance();
		mc.gameSettings.thirdPersonView = 1;

		if (mc.gameSettings.thirdPersonView == 0) {
			mc.gameRenderer.loadEntityShader(mc.getRenderViewEntity());
		} else if (mc.gameSettings.thirdPersonView == 1) {
			mc.gameRenderer.loadEntityShader((Entity) null);
		}
	}
}
