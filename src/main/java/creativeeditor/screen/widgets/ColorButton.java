package creativeeditor.screen.widgets;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class ColorButton extends StyledButton {

    private String c;
    private List<IGuiEventListener> children;

    public ColorButton(List<IGuiEventListener> children, int x, int y, int width, int height, String s, String c) {
        super(x, y, width, height, new StringTextComponent(s), t -> t.onPress());
        this.children = children;
        this.c = c;
    }

    public void onPress() {
        for(IGuiEventListener eventListener : children) {
            if(eventListener instanceof StyledTextField) {
                StyledTextField textField = (StyledTextField) eventListener;
                if(textField.isFocused()) {
                    if(!c.equalsIgnoreCase("clean")) {
                        for(int i = 0; i < c.length(); i++) {
                            textField.charTyped(c.charAt(i), c.charAt(i));
                        }
                    } else {
                        textField.setText(StringUtils.stripColor(textField.getText()));
                    }
                }
            }
        }
    }
}
