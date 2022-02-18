package infinityitemeditor.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.screen.widgets.StyledTextField;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.ItemRendererUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public abstract class ParentScreen extends Screen {
    @Getter
    @Setter
    protected boolean showFrame = true;
    @Getter
    @Setter
    protected boolean showTitle = true;

    protected final Screen lastScreen;
    protected List<Widget> renderWidgets = Lists.newArrayList();

    @Getter
    @Setter
    private int topLineWidth = -1;


    public ParentScreen(ITextComponent title, Screen lastScreen) {
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


    public FontRenderer getFontRenderer() {
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
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
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
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Color color = StyleManager.getCurrentStyle().getMainColor();
        backRender(matrix, mouseX, mouseY, partialTicks, color);
        mainRender(matrix, mouseX, mouseY, partialTicks, color);
        overlayRender(matrix, mouseX, mouseY, partialTicks, color);
        StyleManager.getCurrentStyle().update();
    }


    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        StyleManager.getCurrentStyle().renderBackground(matrix, this);

        if (showTitle) {
            drawCenteredString(matrix, font, getTitle().getString(), width / 2, 9, color.getInt());
        }

        if (!showFrame) {
            return;
        }

        // Frame
        GuiUtil.drawFrame(matrix, 5, 5, width - 5, height - 5, 1, color);

        if (!showTitle) {
            return;
        }
        // Title underline
        int midX = width / 2;
        if (getTopLineWidth() == -1) {
            int sWidthHalf = font.width(getTitle().getString()) / 2 + 3;
            AbstractGui.fill(matrix, midX - sWidthHalf, 20, midX + sWidthHalf, 21, color.getInt());

        } else if (getTopLineWidth() > 1) {
            int halfLineW = topLineWidth / 2;
            AbstractGui.fill(matrix, midX - halfLineW, 20, midX + halfLineW, 21, color.getInt());
        }
    }


    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        buttons.forEach(b -> b.render(matrix, mouseX, mouseY, p3));
        renderWidgets.forEach(w -> w.render(matrix, mouseX, mouseY, p3));
    }


    /**
     * Should always be called last in render, but only once.
     */
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
    }
}
