package creativeeditor.styles;

public class StyleManager {
	private static Style currentStyle;

	public static Style getCurrentStyle() {
		return currentStyle;
	}

	public static void setCurrentStyle(Style style) {
		if (style != null)
			currentStyle = style;
	}
}
