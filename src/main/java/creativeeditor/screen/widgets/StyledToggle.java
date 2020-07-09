package creativeeditor.screen.widgets;

import creativeeditor.data.base.DataBoolean;
import net.minecraft.client.resources.I18n;

public class StyledToggle extends StyledButton {
    private String on, off;


    public StyledToggle(int x, int y, int width, int height, String onText, String offText, DataBoolean toggle) {
        super( x, y, width, height, I18n.format( toggle.get() ? onText : offText ), toggle );
        on = I18n.format( onText );
        off = I18n.format( offText );
    }


    public void updateMessage( boolean b ) {
        setMessage( b ? on : off );
    }
}
