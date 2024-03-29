package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

public class WindowManagerScreen extends Screen {

    public WindowManagerScreen(ITextComponent titleIn) {
        super(titleIn);
    }


    @Override
    protected void init() {
        super.init();
    }


    @Override
    public void resize(Minecraft mc, int width, int height) {
        super.resize(mc, width, height);
    }


    @Override
    public void onClose() {
        super.onClose();
        // minecraft.setScreen( lastScreen );
    }


    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        return super.keyPressed(key1, key2, key3);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }


    public FontRenderer getFontRenderer() {
        return font;
    }


    @Override
    public void tick() {
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Color color = StyleManager.getCurrentStyle().getMainColor();
        backRender(matrix, mouseX, mouseY, partialTicks, color);
        mainRender(matrix, mouseX, mouseY, partialTicks, color);
        overlayRender(mouseX, mouseY, partialTicks, color);
        StyleManager.getCurrentStyle().update();
    }


    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float partialTicks, Color color) {
        this.fillGradient(matrix, 0, 0, this.width, this.height, new Color(150, 16, 16, 16).getInt(), color.copy().setValue(0.4f).setAlpha(150).getInt());
        drawCenteredString(matrix, font, "Heya", width / 2, 10, color.getInt());
    }


    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float partialTicks, Color color) {
        for (Widget w : buttons)
            w.render(matrix, mouseX, mouseY, partialTicks);
    }


    /**
     * Should always be called last in render, but only once.
     */
    public void overlayRender(int mouseX, int mouseY, float partialTicks, Color color) {
    }
}
