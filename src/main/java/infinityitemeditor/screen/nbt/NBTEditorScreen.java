package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class NBTEditorScreen extends WindowScreen {
    protected final Data<?, ?> nbt;

    public NBTEditorScreen(Screen lastScreen, Data<?, ?> nbt) {
        super(lastScreen, new TranslationTextComponent("gui.nbt"));
        this.nbt = nbt;
    }

    @Override
    protected void init() {
        super.init();
        if (windows.isEmpty()) {
            WindowWidget window = new WindowWidget(10, 10, 300, 300, new StringTextComponent("Hello"), new StringTextComponent("Testing"));
            WindowWidget child = new WindowWidget(5, 5, 50, 50, new StringTextComponent("World"), new StringTextComponent("Testing"));
            child.setMinY(0);
            child.setMinX(0);
            window.children.put(child, null);
            windows.add(addButton(window));
        }
        for (WindowWidget w : windows) {
            w.setMinX(6);
            w.setMaxX(width - 6);
            w.setMinY(6);
            w.setMaxY(height - 6);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        return super.keyPressed(key1, key2, key3);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double onX, double onY) {
        return super.mouseDragged(mouseX, mouseY, button, onX, onY);
    }

    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }
}
