package creativeeditor.styles;

import creativeeditor.config.Config;

public class StyleManager {
    private static int currentID = -1;
    private static Style currentStyle;


    public static Style getCurrentStyle() {
        if (currentStyle == null) {
            setCurrentStyle(currentID == -1 ? 0 : currentID);
        }
        return currentStyle;
    }


    private static void setCurrentStyle(Style style) {
        if (style != null) {
            currentStyle = style;
        }
    }


    public static void setCurrentStyle(int id) {
        if (currentID == -1 || currentID != id) {
            setCurrentStyle(id == 0 ? new StyleSpectrum() : new StyleVanilla());
            currentID = id;
        }
    }


    public static void loadConfig() {
        setCurrentStyle(Config.ACTIVESTYLE.get());
    }


    public static void setNext() {
        setCurrentStyle((currentID + 1) % 2);
    }
}
