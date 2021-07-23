package creativeeditor.screen.widgets;

import creativeeditor.data.base.DataBoolean;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;

public class StyledToggle extends StyledButton {
    private final String on;
    private final String off;


    public StyledToggle(int x, int y, int width, int height, String onText, String offText, DataBoolean toggle) {
        super(x, y, width, height, I18n.get(toggle.get() ? onText : offText), toggle);
        on = I18n.get(onText);
        off = I18n.get(offText);
    }


    public void updateMessage(boolean b) {
        setMessage(new StringTextComponent(b ? on : off));
    }
}
