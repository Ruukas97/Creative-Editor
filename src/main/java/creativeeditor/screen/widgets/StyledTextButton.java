package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.styles.StyleManager;
import net.minecraft.client.Minecraft;

public class StyledTextButton extends StyledButton {
    public StyledTextButton(int xIn, int yIn, int stringWidth, String msg, IPressable onPress) {
        super(xIn - (stringWidth / 2) - 1, yIn - 7, stringWidth + 2, 9, msg, onPress);
    }


    public StyledTextButton(WidgetInfo info) {
        this(info.getPosX(), info.getPosY(), info.getWidth(), info.getText(), info.getTrigger());
    }


    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float unused) {
        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            drawString(matrix, mc.font, getMessage(), x + 1, y + 1, StyleManager.getCurrentStyle().getFGColor(this).getInt());
        }
    }


    public static WidgetInfoSupport<StyledTextButton> getWidgetSupport() {
        return StyledTextButton::new;
    }
}
