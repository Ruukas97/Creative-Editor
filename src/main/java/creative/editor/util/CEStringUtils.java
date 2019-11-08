package creative.editor.util;

public class CEStringUtils {

	public static String uppercaseFirstLowercaseRest(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
