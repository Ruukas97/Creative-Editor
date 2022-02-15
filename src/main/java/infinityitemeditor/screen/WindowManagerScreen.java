package infinityitemeditor.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.MutableComponent;

public class WindowManagerScreen extends Screen {

    public WindowManagerScreen(MutableComponent titleIn) {
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


    public Font getFont() {
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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Color color = StyleManager.getCurrentStyle().getMainColor();
        backRender(poseStack,mouseX, mouseY, partialTicks, color);
        mainRender(poseStack,mouseX, mouseY, partialTicks, color);
        overlayRender(mouseX, mouseY, partialTicks, color);
        StyleManager.getCurrentStyle().update();
    }


    public void backRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, Color color) {
        this.fillGradient(poseStack,0, 0, this.width, this.height, new Color(150, 16, 16, 16).getInt(), color.copy().setValue(0.4f).setAlpha(150).getInt());
        drawCenteredString(poseStack,font, "Heya", width / 2, 10, color.getInt());
    }


    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, Color color) {
        for (Widget w : buttons)
            w.render(poseStack,mouseX, mouseY, partialTicks);
    }


    /**
     * Should always be called last in render, but only once.
     */
    public void overlayRender(int mouseX, int mouseY, float partialTicks, Color color) {
    }
}
