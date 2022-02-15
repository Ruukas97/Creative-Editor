package infinityitemeditor.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.screen.widgets.StyledTextField;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.ItemRendererUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class ParentScreen extends Screen {
    protected final Screen lastScreen;
    protected List<Widget> renderWidgets = Lists.newArrayList();

    @Getter
    @Setter
    private int topLineWidth = -1;


    public ParentScreen(MutableComponent title, Screen lastScreen) {
        super(title);
        this.lastScreen = lastScreen;
    }


    @Override
    protected void init() {
        renderWidgets.clear();
        super.init();
    }


    @Override
    public void resize(Minecraft mc, int width, int height) {
        super.resize(mc, width, height);
    }


    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
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


    /**
     * Modified version of
     * {@link net.minecraft.client.gui.screen.inventory.ContainerScreen#renderFloatingItem(ItemStack, int, int, String)}
     * <p>
     * Draws an ItemStack.
     * <p>
     * The z index is increased by 32 (and not decreased afterwards), and the item
     * is then rendered at z=200.
     */
    public void drawItemStack(ItemStack stack, int x, int y, float rotX, float rotY, String altText) {
        RenderSystem.translatef(0.0F, 0.0F, 32.0F);
        this.setBlitOffset(200);
        this.itemRenderer.blitOffset = 10.0F;
        net.minecraft.client.gui.Font font = stack.getItem().getFont(stack);
        if (font == null)
            font = this.font;
        new ItemRendererUtils(itemRenderer).renderItemIntoGUI(stack, x, y, rotX, rotY);
        this.itemRenderer.renderGuiItemDecorations(font, stack, x, y, altText);
        this.setBlitOffset(0);
        this.itemRenderer.blitOffset = 0.0F;
        RenderSystem.translatef(0.0F, 0.0F, -32.0F);
    }


    @Override
    public void tick() {
        renderWidgets.forEach(w -> {
            if (w instanceof StyledTextField) {
                ((StyledTextField) w).tick();
            }
        });
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (Widget w : renderWidgets) {
            if (w.mouseClicked(mouseX, mouseY, mouseButton)) {
                this.setFocused(w);
                if (mouseButton == 0) {
                    this.setDragging(true);
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    @Deprecated
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Color color = StyleManager.getCurrentStyle().getMainColor();
        backRender(poseStack,mouseX, mouseY, partialTicks, color);
        mainRender(poseStack,mouseX, mouseY, partialTicks, color);
        overlayRender(poseStack,mouseX, mouseY, partialTicks, color);
        StyleManager.getCurrentStyle().update();
    }


    public void backRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        StyleManager.getCurrentStyle().renderBackground(poseStack,this);

        // Frame
        GuiUtil.drawFrame(poseStack,5, 5, width - 5, height - 5, 1, color);

        // GUI Title
        drawCenteredString(poseStack,font, getTitle().getString(), width / 2, 9, color.getInt());

        // Title underline
        int midX = width / 2;
        if (getTopLineWidth() == -1) {
            int sWidthHalf = font.width(getTitle().getString()) / 2 + 3;
            AbstractGui.fill(poseStack,midX - sWidthHalf, 20, midX + sWidthHalf, 21, color.getInt());

        } else if (getTopLineWidth() > 1) {
            int halfLineW = topLineWidth / 2;
            AbstractGui.fill(poseStack,midX - halfLineW, 20, midX + halfLineW, 21, color.getInt());
        }
    }


    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        buttons.forEach(b -> b.render(poseStack,mouseX, mouseY, p3));
        renderWidgets.forEach(w -> w.render(poseStack,mouseX, mouseY, p3));
    }


    /**
     * Should always be called last in render, but only once.
     */
    public void overlayRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
    }
}
