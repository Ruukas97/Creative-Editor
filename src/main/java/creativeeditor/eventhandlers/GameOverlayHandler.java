package creativeeditor.eventhandlers;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.CreativeEditor;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.Screenshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GameOverlayHandler {

	public static int duration;
	public static int hold;
	public static int opacity;
	public static double speed;
	public static int x;
	public static int x1;
	public static int y;
	public static int y1;
	public double increaseSpeed;
	static int width;
	static int height;
	public static boolean firstRun = false;
	public static DynamicTexture dyntex;
	private ResourceLocation screenshotLocation;
	public static ScreenshotEvent event;

	@SubscribeEvent
	public void onOverlay(RenderGameOverlayEvent e) {

		if (firstRun) {
			// part of this will be put in the Screenshot class
			width = e.getWindow().getWidth();
			height = e.getWindow().getHeight();
			x = 0;
			x1 = 10;
			y = 0;
			duration = 200;
			speed = ((double) opacity / (double) duration) ;
			increaseSpeed = 0.999;
			this.screenshotLocation = Screenshot.screenshotLocation;
			firstRun = false;
			Minecraft.getInstance().textureManager.loadTexture(screenshotLocation, dyntex);

		}
		if (e.getType() == ElementType.ALL && dyntex != null) {
			if (duration > 1) {

				Color color = new Color(opacity, 255, 255, 255);
				AbstractGui.fill(calculateMove(0, 10, duration), calculateMove(0, 10, duration),
						calculateMove(width, width / 4, duration),
						calculateMove(height, height / 30, duration), color.getInt());
					// calculate move method not working as it's supposed to
				if (hold <= 0) {
					duration--;
					// calculate opacity?

				} else {
					hold--;
				}
				

			}
			if (duration <= 10 && duration > 0) {
				opacity = (int) (duration * speed);
				Minecraft.getInstance().getTextureManager().bindTexture(screenshotLocation);
				GlStateManager.enableBlend();
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				int x1 = 10;
				int x2 = 100;
				GuiUtils.drawInscribedRect(x1, -10, x2, 100, dyntex.getTextureData().getWidth(),
						dyntex.getTextureData().getHeight());
				// View the method above to calculate center for the string
				GlStateManager.pushMatrix();
				GlStateManager.scalef(0.7F, 0.7F, 0.7F);
				char c = (char) CreativeEditor.PREVIEW_SCREENSHOT.getKey().getKeyCode();
				Minecraft.getInstance().fontRenderer.drawStringWithShadow("Press [" + c + "] to view",
						(x2 / 2) + x1 - 16, 100, new Color(255, 255, 255, 255).getInt());
				GlStateManager.popMatrix();
			
				
			}
		}

	}
	
	//100 -> 20 
	// 5 steps
	// 100 - (x * 20) + 20
	private int calculateMove(int start, int end, int duration) {
		return start - ((end / 200) * duration);

	}

}
