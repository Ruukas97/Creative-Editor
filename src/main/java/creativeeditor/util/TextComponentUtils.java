package creativeeditor.util;

import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TextProcessing;

public class TextComponentUtils {
    public static String getPlainText(ITextProperties component) {
        return TextProcessing.getPlainText(component);
    }
}
