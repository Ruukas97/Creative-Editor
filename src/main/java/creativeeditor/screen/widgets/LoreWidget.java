package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.screen.LoreEditorScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LoreWidget extends Widget implements INestedGuiEventHandler {
    private final int spacing = 4;
    private final int squareSize = 20;
    private int count = 0;
    private final int fieldWidth = 200;
    private LoreEditorScreen loreEditorScreen;
    private StyledButton up, down, delete;
    public StyledTextField field;
    private List<Widget> children;

    public LoreWidget(int count, FontRenderer font, LoreEditorScreen loreEditorScreen) {
        super(0, 0, 20, 20, StringTextComponent.EMPTY);
        this.loreEditorScreen = loreEditorScreen;
        this.count = count;
        children = new ArrayList<>();
        field = new StyledTextField(font, 0, 0, fieldWidth, this.height, "lore");
        up = new StyledButton(0, 0, squareSize, this.height, "\u2191", t -> loreEditorScreen.moveUp(this));
        down = new StyledButton(0, 0, squareSize, this.height, "\u2193", t -> loreEditorScreen.moveDown(this));
        delete = new StyledButton(0, 0, squareSize, this.height, "\u2715", t -> loreEditorScreen.removeLine(this));

        children.add(up);
        children.add(down);
        children.add(delete);
        children.add(field);
    }

    public LoreWidget(int count, FontRenderer font, LoreEditorScreen loreEditorScreen, String text) {
        this(count, font, loreEditorScreen);
        field.setText(text);
    }


    @Override
    public List<? extends IGuiEventListener> children() {
        return children;
    }

    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void setDragging(boolean b) {

    }

    @Nullable
    @Override
    public IGuiEventListener getFocused() {
        return null;
    }

    @Override
    public void setFocused(@Nullable IGuiEventListener iGuiEventListener) {

    }

    public LoreWidget updateCount(int i) {
        this.count = i;
        return this;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partial) {
        super.render(matrix, mouseX, mouseY, partial);
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        children.forEach(t -> t.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_));
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    int lastWidth;
    int lastSpacingCount;
    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
        lastWidth = 0;
        lastSpacingCount = 0;
        FontRenderer font = loreEditorScreen.getFontRenderer();
        drawCenteredString(matrix, font, count + ".", this.x + squareSize / 2, this.y + (squareSize - font.lineHeight) / 2, getFGColor());
        renderChild(field, 20, 0, matrix, mouseX, mouseY, partial);
        renderChild(up, fieldWidth, 0, matrix, mouseX, mouseY, partial);
        renderChild(down, 20, 0, matrix, mouseX, mouseY, partial);
        renderChild(delete, 20, 0, matrix, mouseX, mouseY, partial);

    }

    public void renderChild(Widget child, int offsetX, int offsetY, MatrixStack matrix, int mouseX, int mouseY, float partial){
        child.x = this.x + offsetX + lastWidth + (lastSpacingCount * spacing);
        child.y = this.y + offsetY;
        lastWidth += offsetX;
        lastSpacingCount++;
        child.render(matrix, mouseX, mouseY, partial);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        children.forEach(t -> t.charTyped(p_231042_1_,p_231042_2_ ));
        return INestedGuiEventHandler.super.charTyped(p_231042_1_, p_231042_2_);
    }

}
