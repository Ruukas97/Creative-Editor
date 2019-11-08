package creative.editor.config;

import creative.editor.util.ColorUtils;
import creative.editor.util.ColorUtils.Color;

public class CEStyle {

	// GUI color
	public static Color color = CEStyle.vanillaStyle ? new Color(255, 200, 200, 200)
			: new Color(255, ColorUtils.hsvToRGB(5000f, 1f, 1f));;

	// styles
	public static boolean vanillaStyle = true;

}
