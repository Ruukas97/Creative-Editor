package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.CursorService;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WindowWidget extends Widget implements INestedGuiEventHandler {
    public static final int TITLEBAR_HEIGHT = 15;
    public static final int RESIZE_HOVER_MARGIN = 3;


    @Getter
    @Setter
    protected ITextComponent title;
//    @Getter
//    protected ITextComponent extra;

//    protected width;
//    protected height;

    @Getter
    @Setter
    protected Color backgroundColor = new Color(0x99000000);

    @Getter
    @Setter
    protected int minX = -1000;
    @Getter
    @Setter
    protected int minY = -1000;

    @Getter
    @Setter
    protected int maxX = 3000;
    @Getter
    @Setter
    protected int maxY = 3000;

    @Getter
    @Setter
    protected int minWidth = 50;
    @Getter
    @Setter
    protected int minHeight = 50;

    @Getter
    @Setter
    protected int maxWidth = 500;
    @Getter
    @Setter
    protected int maxHeight = 500;

    @Getter
    protected final Map<Widget, WindowChild> children = new HashMap<>();

    @Getter
    @Setter
    protected boolean showTitleBar = true;

    @Getter
    @Setter
    protected boolean canClipScreenBorder = false;

    @Getter
    @Setter
    protected boolean showScrollBar = false;
    @Getter
    @Setter
    protected boolean canScroll = false;
    protected boolean isScrolling = false;
    @Getter
    @Setter
    protected int scrollOffset = 0;

    @Getter
    @Setter
    protected boolean canMove = true;
    protected boolean isMoving = false;

    @Getter
    @Setter
    protected boolean canResize = true;
    protected boolean isResizing = false;
    protected ResizeEdge resizeEdge = null;
    protected Cursor activeCursor = null;

    @Getter
    @Setter
    protected boolean isDragging = false;
    protected int dragOffsetX = 0;
    protected int dragOffsetY = 0;

    @Getter
    @Setter
    protected IGuiEventListener focused = null;


    public WindowWidget(int x, int y, int initialWidth, int initialHeight, ITextComponent title, ITextComponent message) {
        super(x, y, initialWidth, initialHeight, message);
        this.title = title;
        clampPosition();
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        return isHovered && canScroll;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ArrayList<Widget> children() {
        return new ArrayList<>(children.keySet());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        applyMousePosition((int) mouseX, (int) mouseY);
        resizeEdge = getResizeEdge((int) mouseX, (int) mouseY);
        if (canResize && resizeEdge != null) {
            startDragging((int) mouseX, (int) mouseY);
            isResizing = true;
            CursorService.getINSTANCE().setCursor(resizeEdge.getCursor());
            return true;
        }

        if (!isHovered) {
            return false;
        }

        if (canMove && showTitleBar && GuiUtil.isMouseInRegion(mouseX, mouseY, x, y, width, TITLEBAR_HEIGHT)) {
            startDragging((int) mouseX, (int) mouseY);
            isMoving = true;
            return true;
        }
        if (this.children().size() > 0) {
            int localX = x + 1;
            int localY = y + 1;
            if (showTitleBar) {
                localY += TITLEBAR_HEIGHT;
            }
            int localMouseX = (int) (mouseX - localX);
            int localMouseY = (int) (mouseY - localY);
            for (Widget child : this.children()) {
                if (child.mouseClicked(localMouseX, localMouseY, mouseButton)) {
                    this.setFocused(child);
                    if (mouseButton == 0) {
                        isDragging = true;
                    }
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void startDragging(int mouseX, int mouseY) {
        isDragging = true;
        dragOffsetX = mouseX - x;
        dragOffsetY = mouseY - y;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        applyMousePosition((int) mouseX, (int) mouseY);
        if (activeCursor != null || isScrolling) {
            CursorService.getINSTANCE().resetCursor();
            activeCursor = null;
        }
        isDragging = false;
        dragOffsetX = 0;
        dragOffsetY = 0;
        isMoving = false;
        isScrolling = false;
        isResizing = false;
        resizeEdge = null;
        if (this.children().size() > 0) {
            int localX = x + 1;
            int localY = y + 1;
            if (showTitleBar) {
                localY += TITLEBAR_HEIGHT;
            }
            int localMouseX = (int) (mouseX - localX);
            int localMouseY = (int) (mouseY - localY);
            for (Widget child : this.children()) {
                child.mouseReleased(localMouseX, localMouseY, mouseButton);
            }
        }
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double onX, double onY) {
        applyMousePosition((int) mouseX, (int) mouseY);
        if (this.children().size() > 0) {
            int localX = x + 1;
            int localY = y + 1;
            if (showTitleBar) {
                localY += TITLEBAR_HEIGHT;
            }
            int localMouseX = (int) (mouseX - localX);
            int localMouseY = (int) (mouseY - localY);
            for (Widget child : this.children()) {
                child.mouseDragged(localMouseX, localMouseY, button, onX, onY);
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, onX, onY);
    }

    public void applyMousePosition(int mouseX, int mouseY) {
        MainWindow window = Minecraft.getInstance().getWindow();
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        int xLimit = maxX - minWidth;
        int yLimit = maxY - minHeight;
        if (isResizing && resizeEdge != null) {
            if (resizeEdge == ResizeEdge.LEFT || resizeEdge == ResizeEdge.TOPLEFT || resizeEdge == ResizeEdge.BOTTOMLEFT) {
                int originalX = x;
                x = MathHelper.clamp(mouseX, Math.max(canClipScreenBorder ? minX : Math.max(minX, 0), x + width - maxWidth), Math.min(x + width - minWidth, xLimit));
                width += originalX - x;
            } else if (resizeEdge == ResizeEdge.RIGHT || resizeEdge == ResizeEdge.TOPRIGHT || resizeEdge == ResizeEdge.BOTTOMRIGHT) {
                width = MathHelper.clamp(mouseX - x, minWidth, Math.min(maxX - x, canClipScreenBorder ? Math.min(maxWidth, screenWidth) : maxWidth));
            }
            if (resizeEdge == ResizeEdge.TOP || resizeEdge == ResizeEdge.TOPLEFT || resizeEdge == ResizeEdge.TOPRIGHT) {
                int originalY = y;
                y = MathHelper.clamp(mouseY, Math.max(canClipScreenBorder ? minY : Math.max(minY, 0), y + height - maxHeight), Math.min(y + height - minHeight, yLimit));
                height += originalY - y;
            } else if (resizeEdge == ResizeEdge.BOTTOM || resizeEdge == ResizeEdge.BOTTOMLEFT || resizeEdge == ResizeEdge.BOTTOMRIGHT) {
                height = MathHelper.clamp(mouseY - y, minHeight, Math.min(maxY - y, canClipScreenBorder ? Math.min(maxHeight, screenHeight) : maxHeight));
            }
        } else if (isMoving) {
            x = mouseX - dragOffsetX;
            y = mouseY - dragOffsetY;
            clampPosition();
        }
    }

    public void clampPosition() {
        MainWindow window = Minecraft.getInstance().getWindow();
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        int xLimit = maxX - width;
        int yLimit = maxY - height;
        if (!canClipScreenBorder) {
            if (x < 0) {
                x = 0;
            } else if (x > screenWidth - width) {
                x = screenWidth - width;
            }
            if (y < 0) {
                y = 0;
            } else if (y > screenHeight - height) {
                y = screenHeight - height;
            }
        }
        x = MathHelper.clamp(x, minX, xLimit);
        y = MathHelper.clamp(y, minY, yLimit);
    }

    public ResizeEdge getResizeEdge(int mouseX, int mouseY) {
        int doubleMargin = 2 * RESIZE_HOVER_MARGIN;
        boolean left = GuiUtil.isMouseInRegion(mouseX, mouseY, x - RESIZE_HOVER_MARGIN, y - RESIZE_HOVER_MARGIN, doubleMargin, height + doubleMargin);
        boolean right = GuiUtil.isMouseInRegion(mouseX, mouseY, x + width - RESIZE_HOVER_MARGIN, y - RESIZE_HOVER_MARGIN, doubleMargin, height + doubleMargin);
        boolean top = GuiUtil.isMouseInRegion(mouseX, mouseY, x - RESIZE_HOVER_MARGIN, y - RESIZE_HOVER_MARGIN, width + doubleMargin, doubleMargin);
        boolean bottom = GuiUtil.isMouseInRegion(mouseX, mouseY, x - RESIZE_HOVER_MARGIN, y + height - RESIZE_HOVER_MARGIN, width + doubleMargin, doubleMargin);
        return ResizeEdge.byEdges(left, right, top, bottom);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        //isHovered = GuiUtil.isMouseIn(mouseX, mouseY, x, y, width, height);

        if (canResize && !isResizing) {
            ResizeEdge edge = getResizeEdge(mouseX, mouseY);
            if (edge != null) {
                if (activeCursor == null) {
                    CursorService.getINSTANCE().setCursor(edge.getCursor());
                    activeCursor = edge.getCursor();
                }
            } else if (activeCursor != null) {
                CursorService.getINSTANCE().resetCursor();
                activeCursor = null;
            }
        }

        Color color = StyleManager.getCurrentStyle().getMainColor();
        GuiUtil.drawFrame(matrix, x, y, x + width, y + height, 1, color);

        if (backgroundColor != null) {
            AbstractGui.fill(matrix, x + 1, y + 1, x + width - 2, y + height - 2, backgroundColor.getInt());
        }

        if (showTitleBar) {
            AbstractGui.fill(matrix, x + 10, y + TITLEBAR_HEIGHT - 1, x + width - 10, y + TITLEBAR_HEIGHT, color.getInt());
            drawCenteredString(matrix, Minecraft.getInstance().font, title, x + width / 2, y + TITLEBAR_HEIGHT - 11, color.getInt());
        }

        if (children.size() == 0) {
            return;
        }

        RenderUtil.glScissorBox(x + 1, y + 2, x + width - 1, y + height - 1);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        boolean mouseOut = mouseY < y || mouseY >= y + height;
        if (mouseOut) {
            mouseX = -10000;
            mouseY = -10000;
        }

        int localX = x + 1;
        int localY = y + 1;
        if (showTitleBar) {
            localY += TITLEBAR_HEIGHT;
        }
        int localMouseX = mouseX - localX;
        int localMouseY = mouseY - localY;

        GL11.glTranslated(localX, localY, 0d);
        for (Widget w : children.keySet()) {
            w.render(matrix, localMouseX, localMouseY, partialTicks);
        }
        GL11.glTranslated(-localX, -localY, 0d);


        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void playDownSound(SoundHandler soundHandler) {
    }
}
