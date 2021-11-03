package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.styles.IStyledButton;
import creativeeditor.styles.StyleManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class StyledButton extends Button implements IStyledButton {
    @Getter
    @Setter
    private String tooltip;

    public StyledButton(WidgetInfo info) {
        this(info.getPosX(), info.getPosY(), info.getWidth(), info.getHeight(), info.getText(), info.getTrigger());
    }

    public StyledButton(int x, int y, int width, int height, ITextComponent text, IPressable onPress) {
        super(x, y, width, height, text, onPress);
    }

    public StyledButton(int x, int y, int width, int height, String text, IPressable onPress) {
        super(x, y, width, height, new StringTextComponent(text), onPress);
    }


    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float unused) {
        StyleManager.getCurrentStyle().renderButton(matrix, this, mouseX, mouseY, alpha);
    }

    @Override
    public int getFGColor() {
        return StyleManager.getCurrentStyle().getFGColor(this).getInt();
    }


    @Override
    public Widget getWidget() {
        return this;
    }

    @Override
    public int getImageY(boolean b) {
        return super.getYImage(b);
    }

    @Override
    public int getOffsetBlit() {
        return super.getBlitOffset();
    }

    @Override
    public void setHovered(boolean b) {
        isHovered = b;
    }

    @Override
    public void renderBackground(MatrixStack matrix, Minecraft mc, int mouseX, int mouseY) {
        super.renderBg(matrix, mc, mouseX, mouseY);
    }
}
