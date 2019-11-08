package creativeeditor.config.styles;

import creativeeditor.util.ColorUtils;
import creativeeditor.util.ColorUtils.Color;

public class ColorStyles {
	// styles
	private static Style style = Style.vanilla;
	private static Color staticColor = Style.vanilla.getColor();
	private static int spectrumTick = 0;

	public static enum Style {
		spectrum, vanilla, staticStyle;

		public Color getColor() {
			switch (this) {
			case spectrum:
				spectrumTick = (spectrumTick+1)%5000;
				return new Color(255, ColorUtils.hsvToRGB(spectrumTick/5000f, 1f, 1f));
			case vanilla:
				return new Color(255, 200, 200, 200);
			case staticStyle:
				if (staticColor != null)
					return staticColor;
			}

			return new Color(255, 200, 200, 200);
		}
	}
	
	public static Style getStyle() {
		return style;
	}

	public static void setStyle(Style style) {
		ColorStyles.style = style;
	}

	public static Color getMainColor() {
		return style.getColor();
	}
	
}
