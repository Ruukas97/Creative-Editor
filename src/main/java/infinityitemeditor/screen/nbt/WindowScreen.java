package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class WindowScreen extends ParentScreen {
    protected final List<WindowWidget> windows = new ArrayList<>();

    public WindowScreen(Screen lastScreen, TranslationTextComponent title) {
        super(title, lastScreen);
    }

    @Override
    protected void init() {
        super.init();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        for (WindowWidget window : windows) {
            addButton(window);
            window.clampPosition();
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
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
        for (WindowWidget window : windows) {
            if (window.isDragging) {
                window.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double onX, double onY) {
        for (WindowWidget window : windows) {
            if (window.isDragging) {
                window.mouseDragged(mouseX, mouseY, button, onX, onY);
            }
        }
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
