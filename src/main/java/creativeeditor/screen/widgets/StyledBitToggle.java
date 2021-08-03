package creativeeditor.screen.widgets;

import creativeeditor.data.base.DataBitField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;

public class StyledBitToggle extends StyledButton {
    public int index;
    private static String divider = ": ";
    private static String on =  I18n.get("gui.toggle.on");
    private static String off = I18n.get("gui.toggle.off");
    private String onText;
    private String offText;

    public StyledBitToggle(int x, int y, int width, int height, String onText, String offText, DataBitField bitField, int index) {
        super(x, y, width, height, bitField.get()[index] ? onText : offText, bitField);
        this.onText = onText;
        this.offText = offText;
        this.index = index;
    }

    public StyledBitToggle(int x, int y, int width, int height, String text, DataBitField bitField, int index) {
        super(x, y, width, height, (!text.isEmpty() ? text + divider : "") + (bitField.get()[index] ? on : off), bitField);
        this.onText = (!text.isEmpty() ? text + divider : "") + on;
        this.offText = (!text.isEmpty() ? text + divider : "") + off;
        this.index = index;
    }


    public void updateMessage(boolean b) {
        setMessage(new StringTextComponent(b ? onText : offText));
    }
}
