package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.StyledTextField;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TagExplorerScreen extends ParentScreen {

    private DataItem item;
    private StyledTextField textField;

    public TagExplorerScreen(Screen lastScreen, DataItem item) {
        super(new TranslationTextComponent("gui.tagexplorer"), lastScreen);
        this.item = item;
    }

    @Override
    protected void init() {
        super.init();
        textField = new StyledTextField(minecraft.font, 10, 10, 100, 20, "Yes");
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        textField.render(matrix, mouseY, mouseY, p3);
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        textField.keyPressed(key1, key2, key3);
        return super.keyPressed(key1, key2, key3);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        textField.mouseClicked(mouseX, mouseY, mouseButton);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void tick() {
        textField.tick();
        super.tick();
    }
}
