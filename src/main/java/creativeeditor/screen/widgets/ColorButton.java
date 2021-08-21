package creativeeditor.screen.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class ColorButton extends StyledButton {

    private String c;
    private List<IGuiEventListener> children;
    private boolean clean;

    public ColorButton(List<IGuiEventListener> children, int x, int y, int width, int height, String s, String c, boolean clean) {
        super(x, y, width, height, new StringTextComponent(s), t -> t.onPress());
        this.children = children;
        this.c = c;
        this.clean = clean;

        if (clean && children == null) active = false;
    }

    public ColorButton(List<IGuiEventListener> children, int x, int y, int width, int height, String s, String c) {
        this(children, x, y, width, height, s, c, false);
    }

    public void onPress() {
        if (children == null) {
            for (int i = 0; i < c.length(); i++) {
                Minecraft.getInstance().screen.charTyped(c.charAt(i), c.charAt(i));
            }
            return;
        }
        for (IGuiEventListener eventListener : children) {
            if (!(eventListener instanceof StyledTextField)) continue;
            StyledTextField textField = (StyledTextField) eventListener;
            if (!textField.isFocused()) continue;
            if (!clean) {
                for (int i = 0; i < c.length(); i++) {
                    textField.charTyped(c.charAt(i), c.charAt(i));
                }
            } else {
                textField.setText(StringUtils.stripColor(textField.getText()));
            }
        }

    }
}
