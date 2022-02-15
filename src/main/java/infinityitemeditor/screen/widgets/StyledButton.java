package infinityitemeditor.screen.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.styles.IStyledButton;
import infinityitemeditor.styles.StyleManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.MutableComponent;
import net.minecraft.util.text.TextComponent;

public class StyledButton extends Button implements IStyledButton {
    @Getter
    @Setter
    private String tooltip;

    public StyledButton(WidgetInfo info) {
        this(info.getPosX(), info.getPosY(), info.getWidth(), info.getHeight(), info.getText(), info.getTrigger());
    }

    public StyledButton(int x, int y, int width, int height, MutableComponent text, Button.OnPress onPress) {
        super(x, y, width, height, text, onPress);
    }

    public StyledButton(int x, int y, int width, int height, String text, Button.OnPress onPress) {
        super(x, y, width, height, new TextComponent(text), onPress);
    }


    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float unused) {
        StyleManager.getCurrentStyle().renderButton(poseStack, this, mouseX, mouseY, alpha);
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
    public void renderBackground(PoseStack poseStack, Minecraft mc, int mouseX, int mouseY) {
        super.renderBg(poseStack,mc, mouseX, mouseY);
    }
}
