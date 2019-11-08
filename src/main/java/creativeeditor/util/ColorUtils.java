package creativeeditor.util;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColorUtils {	
	public static int rgb(int r, int g, int b) {
		return MathHelper.rgb(r, g, b);
	}

	public static int multiplyColor(int color, int color2) {
		return MathHelper.multiplyColor(color, color2);
	}
	
	public static int hsvToRGBInt(float hue, float saturation, float value) {
		return MathHelper.hsvToRGB(hue/255f, saturation/255f, value/255f);
	}

	public static int hsvToRGB(float hue, float saturation, float value) {
		return MathHelper.hsvToRGB(hue, saturation, value);
	}
	
	public static class Color{
		int color;
		
		public Color(int color) {
			this.color = color;
		}
		
		public Color(int a, int r, int g, int b) {
			this.color = (a << 24) + (r << 16) + (g << 8) + b;
		}
		
		public Color(int a, int rgb) {
			this.color = (a << 24) + rgb;
		}
		
		public int getColor() {
			return color;
		}
	}
}
