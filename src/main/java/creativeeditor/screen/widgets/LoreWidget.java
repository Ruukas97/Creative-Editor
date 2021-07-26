package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.screen.LoreEditorScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;

public class LoreWidget extends Widget {


    private int id;
    private int squareSize = 20;
    private LoreEditorScreen loreEditorScreen;
    private StyledButton up, down, delete;
    private StyledTextField field;

    public LoreWidget(int id, FontRenderer font, LoreEditorScreen loreEditorScreen) {
        super(0, 0, 20, 20, StringTextComponent.EMPTY);
        this.id = id;
        this.loreEditorScreen = loreEditorScreen;
        up = new StyledButton(3, 0, squareSize, squareSize, "↑", null);
        down = new StyledButton(4, 0, squareSize, squareSize, "↓", null);
        delete = new StyledButton(5, 0, squareSize, squareSize, "↓", t -> loreEditorScreen.removeLine(this));
        field = new StyledTextField(font, 2 , 10, 10, 10, "lore");
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partial) {
        up.render(matrix, mouseX, mouseY, partial);
        down.render(matrix, mouseX, mouseY, partial);
        delete.render(matrix, mouseX, mouseY, partial);
        field.render(matrix, mouseX, mouseY, partial);
    }

}
