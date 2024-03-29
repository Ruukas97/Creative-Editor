package infinityitemeditor.screen.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

public class ScrollableScissorWindow extends Widget implements INestedGuiEventHandler {
    @Nullable
    @Getter
    @Setter
    private IGuiEventListener focused;
    @Getter
    @Setter
    private boolean isDragging;

    @Getter
    private boolean isScrolling = false;
    @Getter
    @Setter
    private int scrollOffset = 0;
    @Getter
    @Setter
    private int padding = 5;
    @Getter
    @Setter
    private int scrollBarWidth = 10;

    @Getter
    protected final List<Widget> widgets = Lists.newArrayList();

    public ScrollableScissorWindow(int x, int y, int width, int height, ITextComponent msg) {
        super(x, y, width, height, msg);
    }


    public int getListHeight() {
        int height = 5;
        for (Widget w : widgets) {
            height += w.getHeight() + padding;
        }
        return height;
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        if (isHovered) {
            int listHeight = getListHeight();
            if (listHeight <= height)
                return false;
            scrollOffset = (int) MathHelper.clamp(scrollOffset - p_mouseScrolled_5_ * 10, 0, listHeight - height);
            return true;
        }
        return false;
    }

    @Override
    public List<? extends IGuiEventListener> children() {
        return widgets;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 && GuiUtil.isMouseInRegion(mouseX, mouseY, x + width - scrollBarWidth, y, scrollBarWidth, height)) {
            isScrolling = true;

            int listHeight = getListHeight();
            int max = listHeight - this.height;
            int insideHeight = (this.height - 4);
            double covered = MathHelper.clamp(insideHeight / (float) listHeight, 0d, 1d);
            int scrollBarHeight = (int) (insideHeight * covered);
            int scrollSpace = insideHeight - scrollBarHeight;
            double mousePos = MathHelper.clamp(mouseY - (this.y + 2 + (scrollBarHeight / 2d)), 0, scrollSpace);
            double scrollPerc = mousePos / scrollSpace;
            scrollOffset = (int) (scrollPerc * max);
            return true;
        } else {
            isScrolling = false;
        }

        if (this.isHovered && this.children().size() > 0) {
            for (IGuiEventListener iguieventlistener : this.children()) {
                if (iguieventlistener.mouseClicked(mouseX, mouseY, mouseButton)) {
                    this.setFocused(iguieventlistener);
                    if (mouseButton == 0) {
                        this.setDragging(true);
                    }

                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        isScrolling = false;
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double onX, double onY) {
        if (isScrolling) {
            int listHeight = getListHeight();
            int max = listHeight - this.height;
            int insideHeight = (this.height - 4);
            double covered = MathHelper.clamp(insideHeight / (float) listHeight, 0d, 1d);
            int scrollBarHeight = (int) (insideHeight * covered);
            int scrollSpace = insideHeight - scrollBarHeight;
            double mousePos = MathHelper.clamp(mouseY - (this.y + 2 + (scrollBarHeight / 2)), 0, scrollSpace);
            double scrollPerc = mousePos / scrollSpace;
            scrollOffset = (int) (scrollPerc * max);
        }
        return super.mouseDragged(mouseX, mouseY, button, onX, onY);
    }

    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        // super.renderButton( mouseX, mouseY, partialTicks );
        // Minecraft mc = Minecraft.getInstance();
        GuiUtil.drawFrame(matrix, x, y, x + width, y + height, 1, StyleManager.getCurrentStyle().getMainColor());
        int listHeight = getListHeight();
        GuiUtil.drawFrame(matrix, x + width - scrollBarWidth, y, x + width, y + height, 1, StyleManager.getCurrentStyle().getMainColor());
        int insideHeight = (this.height - 4);
        double covered = MathHelper.clamp(insideHeight / (float) listHeight, 0d, 1d);
        int scrollBarHeight = (int) (insideHeight * covered);
        double scrolled = MathHelper.clamp(scrollOffset / (float) (listHeight - this.height), 0d, 1d);
        int scrolledPixels = (int) (scrolled * (insideHeight - scrollBarHeight));
        int scrollTop = 2 + y + scrolledPixels;
        boolean hovered = GuiUtil.isMouseInRegion(mouseX, mouseY, x + width - scrollBarWidth, y, scrollBarWidth, height);
        fill(matrix, 2 + x + width - scrollBarWidth, scrollTop, x + width - 2, scrollTop + scrollBarHeight, StyleManager.getCurrentStyle().getFGColor(true, hovered).getInt());

        if (widgets.size() != 0) {
            RenderUtil.glScissorBox(this.x + 1, this.y + 2, this.x + this.width - scrollBarWidth - 1, this.y + this.height - 1);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            int x = this.x + padding;
            int y = this.y + padding - scrollOffset;
            int maxY = this.y + this.height;

            boolean mouseOut = mouseY < this.y || mouseY >= maxY;

            for (Widget w : widgets) {
                w.x = x;
                w.y = y;
                w.setWidth(width - padding - padding - scrollBarWidth);

                if (this.y <= w.y + w.getHeight() && w.y < maxY) {
                    if (mouseOut) {
                        w.render(matrix, -10000, -10000, partialTicks);
                    } else {
                        w.render(matrix, mouseX, mouseY, partialTicks);
                    }
                }
                y = w.y + w.getHeight() + padding;
            }

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }

    @Override
    public void playDownSound(SoundHandler p_230988_1_) {
    }
}
