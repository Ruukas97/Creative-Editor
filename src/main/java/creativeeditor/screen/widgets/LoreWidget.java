package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.screen.LoreEditorScreen;
import lombok.Getter;
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
    @Getter
    private int count;
    private final int offset = 20;
    private final int fieldWidth = width - (squareSize * 3 + (offset * 2 + (offset / 2)));
    private LoreEditorScreen loreEditorScreen;
    private FontRenderer font;
    private StyledButton up, down, delete;
    public StyledTextField field;
    private List<Widget> children;

    public LoreWidget(int count, int width, FontRenderer font, LoreEditorScreen loreEditorScreen) {
        super(0, 0, width, 20, StringTextComponent.EMPTY);
        this.loreEditorScreen = loreEditorScreen;
        this.count = count;
        this.font = font;
        children = new ArrayList<>();
        field = new StyledTextField(font, 0, 0, fieldWidth, this.height, "lore");
        field.setMaxStringLength(9999);
        up = new StyledButton(0, 0, squareSize, this.height, "\u2191", t -> loreEditorScreen.moveUp(this));
        down = new StyledButton(0, 0, squareSize, this.height, "\u2193", t -> loreEditorScreen.moveDown(this));
        delete = new StyledButton(0, 0, squareSize, this.height, "\u2715", t -> loreEditorScreen.removeLine(this));

        children.add(up);
        children.add(down);
        children.add(delete);
        children.add(field);
    }

    public LoreWidget(int count, int width, FontRenderer font, LoreEditorScreen loreEditorScreen, String text) {
        this(count, width, font, loreEditorScreen);
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



    public LoreWidget updateCount(int i) {
        this.count = i;
        return this;
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
        renderChild(field, offset, 0, matrix, mouseX, mouseY, partial);
        renderChild(up, fieldWidth, 0, matrix, mouseX, mouseY, partial);
        renderChild(down, offset, 0, matrix, mouseX, mouseY, partial);
        renderChild(delete, offset, 0, matrix, mouseX, mouseY, partial);

    }

    public void renderChild(Widget child, int offsetX, int offsetY, MatrixStack matrix, int mouseX, int mouseY, float partial) {
        child.x = this.x + offsetX + lastWidth + (lastSpacingCount * spacing);
        child.y = this.y + offsetY;
        lastWidth += offsetX;
        lastSpacingCount++;
        child.render(matrix, mouseX, mouseY, partial);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        return field.charTyped(p_231042_1_, p_231042_2_);
    }

    @Nullable
    @Override
    public IGuiEventListener getFocused() {
        return this.field;
    }

    @Override
    public void setFocused(@Nullable IGuiEventListener p_231035_1_) {
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return field.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    public void setFocusField(boolean bool) {
        this.setFocused(bool);
        field.setFocused(bool);
        field.selectionEnd = field.cursorPosition;
    }

    public String getText() {
        return field.getText();
    }

    @Override
    public boolean isFocused() {
        return field.isFocused();
    }
}
