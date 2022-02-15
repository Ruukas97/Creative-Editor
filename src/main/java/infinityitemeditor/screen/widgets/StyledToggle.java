package infinityitemeditor.screen.widgets;

import infinityitemeditor.data.base.DataBoolean;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.text.TextComponent;

public class StyledToggle extends StyledButton {

    private static String divider = ": ";
    private static String on =  I18n.get("gui.toggle.on");
    private static String off = I18n.get("gui.toggle.off");
    private String onText;
    private String offText;

    public StyledToggle(int x, int y, int width, int height, String onText, String offText, DataBoolean toggle) {
        super(x, y, width, height, toggle.get() ? onText : offText, toggle);
        this.onText = onText;
        this.offText = offText;
    }

    public StyledToggle(int x, int y, int width, int height, String text, DataBoolean toggle) {
        super(x, y, width, height, (!text.isEmpty() ? text + divider : "") + (toggle.get() ? on : off), toggle);
        this.onText = (!text.isEmpty() ? text + divider : "") + on;
        this.offText = (!text.isEmpty() ? text + divider : "") + off;
    }


    public void updateMessage(boolean b) {
        setMessage(new TextComponent(b ? onText : offText));
    }
}
