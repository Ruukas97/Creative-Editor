package creativeeditor.util;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColorUtils {
	public static Color multiplyColor(Color color, Color color2) {
		return new Color(MathHelper.multiplyColor(color.getInt(), color2.getInt()));
	}

	public static Color hsvToRGBInt(float hue, float saturation, float value) {
		return new Color(255, MathHelper.hsvToRGB(hue / 255f, saturation / 255f, value / 255f));
	}

	public static Color hsvToRGB(float hue, float saturation, float value) {
		return new Color(255, MathHelper.hsvToRGB(hue, saturation, value));
	}

	public static class Color {
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

		public int getInt() {
			return color;
		}
	}
}
