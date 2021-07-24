package creativeeditor.screen.widgets;

import creativeeditor.data.base.DataBoolean;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class StyledTFToggle extends StyledButton {
    private final String text;


    public StyledTFToggle(int x, int y, int width, int height, String text, DataBoolean toggle) {
        super(x, y, width, height, I18n.get(text) + ": " + toggle.get().toString(), toggle);
        this.text = I18n.get(text);
    }


    public void updateMessage(boolean b) {
        setMessage(new StringTextComponent(text + ": " + b));
    }
}
