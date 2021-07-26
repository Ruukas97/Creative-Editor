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
    private int id;
    private int squareSize = 20;
    private LoreEditorScreen loreEditorScreen;
    private StyledButton up, down, delete;
    private StyledTextField field;
    private List<Widget> children;

    public LoreWidget(int id, FontRenderer font, LoreEditorScreen loreEditorScreen) {
        super(0, 0, 20, 20, StringTextComponent.EMPTY);
        this.id = id;
        this.loreEditorScreen = loreEditorScreen;
        children = new ArrayList<>();
        up = new StyledButton(0, 0, squareSize, this.height, "↑", null);
        down = new StyledButton(0, 0, squareSize, this.height, "↓", null);
        delete = new StyledButton(0, 0, squareSize, this.height, "↓", t -> loreEditorScreen.removeLine(this));
        field = new StyledTextField(font, 0, 0, 10, 10, "lore");
        children.add(up);
        children.add(down);
        children.add(delete);
        children.add(field);
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


    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partial) {
        super.render(matrix, mouseX, mouseY, partial);
    }


    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
        System.out.println("Hello2");
        up.x = this.x;
        up.y = this.y;
        up.render(matrix, mouseX, mouseY, partial);
        down.x = this.x + 20;
        down.y = this.y;
        down.render(matrix, mouseX, mouseY, partial);
        delete.x = this.x + 40;
        delete.y = this.y;
        delete.render(matrix, mouseX, mouseY, partial);
        field.x = this.x + 60;
        field.y = this.y;
        field.render(matrix, mouseX, mouseY, partial);
    }
}
