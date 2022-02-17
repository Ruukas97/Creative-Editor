package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.CursorService;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.RemoveWidgetCallback;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public class WindowWidget extends Widget implements INestedGuiEventHandler {
    public static final ResourceLocation CLOSE_ICON = new ResourceLocation(InfinityItemEditor.MODID, "textures/gui/close.png");
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
    protected Color color = null;

    @Getter
    @Setter
    protected Color backgroundColor = new Color(0x99000000);

    @Getter
    @Setter
    protected EdgeInsets insets = EdgeInsets.ZERO;

    @Getter
    @Setter
    protected Constraints sizeConstraints = new Constraints(50, 2000, 50, 2000);

    @Getter
    @Setter
    protected Size screenSize;
    @Getter
    @Setter
    protected boolean canClipScreenBorder = false;

    @Getter
    protected final LinkedList<Widget> children = new LinkedList<>();

    @Getter
    @Setter
    protected boolean showTitleBar = true;

    @Getter
    protected boolean hasCloseButton = false;
    @Getter
    protected RemoveWidgetCallback closeCallback = null;

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


    public WindowWidget(int x, int y, int initialWidth, int initialHeight, ITextComponent title, ITextComponent message, Size screenSize) {
        super(x, y, initialWidth, initialHeight, message);
        this.title = title;
        this.screenSize = screenSize;
        clampPosition();
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        return isHovered && canScroll;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<Widget> children() {
        return children;
    }

    public void setCloseable(RemoveWidgetCallback callback) {
        hasCloseButton = true;
        closeCallback = callback;
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

        if (showTitleBar && GuiUtil.isMouseInRegion(mouseX, mouseY, x, y, width, TITLEBAR_HEIGHT)) {
            int closeX = x + width - 14;
            int closeY = y + 1;
            if (hasCloseButton && GuiUtil.isMouseInRegion(mouseX, mouseY, closeX, closeY, 13, 13)) {
                closeCallback.removeWidget(this);
                return true;
            } else if (canMove) {
                startDragging((int) mouseX, (int) mouseY);
                isMoving = true;
                return true;
            }
        }
        if (this.children().size() > 0) {
            int localX = x + 1;
            int localY = y + 1;
            if (showTitleBar) {
                localY += TITLEBAR_HEIGHT - 1;
            }
            int localMouseX = (int) (mouseX - localX);
            int localMouseY = (int) (mouseY - localY);
            List<Widget> widgets = this.children();
            for (int i = 0; i < widgets.size(); i++) {
                Widget child = widgets.get(i);
                if (child.mouseClicked(localMouseX, localMouseY, mouseButton)) {
                    this.setFocused(child);
                    if (mouseButton == 0) {
                        isDragging = true;
                    }
                    children.addFirst(children.remove(i));
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
                localY += TITLEBAR_HEIGHT - 1;
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
                localY += TITLEBAR_HEIGHT - 1;
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

        Constraints constraints = sizeConstraints;
        if (constraints == null) {
            constraints = Constraints.NONE;
        }
        Size size = screenSize;
        if (size == null) {
            MainWindow window = Minecraft.getInstance().getWindow();
            size = new Size(window.getGuiScaledWidth(), window.getGuiScaledHeight());
        }

        if (isResizing && resizeEdge != null) {
            if (resizeEdge == ResizeEdge.LEFT || resizeEdge == ResizeEdge.TOPLEFT || resizeEdge == ResizeEdge.BOTTOMLEFT) {
                int originalX = x;
                x = MathHelper.clamp(mouseX, x + width - constraints.maxWidth(), x + width - constraints.minWidth());
                clampPosition();
                width += originalX - x;
            } else if (resizeEdge == ResizeEdge.RIGHT || resizeEdge == ResizeEdge.TOPRIGHT || resizeEdge == ResizeEdge.BOTTOMRIGHT) {
                width = MathHelper.clamp(mouseX - x, constraints.minWidth(), Math.min(constraints.maxWidth(), size.width() - x));
            }
            if (resizeEdge == ResizeEdge.TOP || resizeEdge == ResizeEdge.TOPLEFT || resizeEdge == ResizeEdge.TOPRIGHT) {
                int originalY = y;
                y = MathHelper.clamp(mouseY, y + height - constraints.maxHeight(), y + height - constraints.minHeight());
                clampPosition();
                height += originalY - y;
            } else if (resizeEdge == ResizeEdge.BOTTOM || resizeEdge == ResizeEdge.BOTTOMLEFT || resizeEdge == ResizeEdge.BOTTOMRIGHT) {
                height = MathHelper.clamp(mouseY - y, constraints.minHeight(), Math.min(constraints.maxHeight(), size.height() - y));
            }
            Size contentSize = new Size(width, height - TITLEBAR_HEIGHT);

            for (Widget child : this.children()) {
                if (child instanceof WindowWidget) {
                    WindowWidget w = (WindowWidget) child;
                    w.setScreenSize(contentSize);
                    w.clampPosition();
                }
            }
        } else if (isMoving) {
            x = mouseX - dragOffsetX;
            y = mouseY - dragOffsetY;
            clampPosition();
        }
    }

    public void clampPosition() {
        Size size = screenSize;
        if (size == null) {
            MainWindow window = Minecraft.getInstance().getWindow();
            size = new Size(window.getGuiScaledWidth(), window.getGuiScaledHeight());
        }

        if (!canClipScreenBorder) {
            x = MathHelper.clamp(x, insets.left(), size.width() - insets.right() - width);
            y = MathHelper.clamp(y, insets.top(), size.height() - insets.bottom() - height);
        }
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
                if (activeCursor != edge.getCursor()) {
                    activeCursor = edge.getCursor();
                    CursorService.getINSTANCE().setCursor(activeCursor);
                }
            } else if (activeCursor != null) {
                CursorService.getINSTANCE().resetCursor();
                activeCursor = null;
            }
        }

        Color color = this.color;
        if (color == null) {
            color = StyleManager.getCurrentStyle().getMainColor();
        }

        GuiUtil.drawFrame(matrix, x, y, x + width, y + height, 1, color);

        if (backgroundColor != null) {
            AbstractGui.fill(matrix, x + 1, y + 1, x + width - 2, y + height - 2, backgroundColor.getInt());
        }

        if (showTitleBar) {
            AbstractGui.fill(matrix, x + 10, y + TITLEBAR_HEIGHT - 1, x + width - 10, y + TITLEBAR_HEIGHT, color.getInt());
            drawCenteredString(matrix, Minecraft.getInstance().font, title, x + width / 2, y + TITLEBAR_HEIGHT - 11, color.getInt());
        }

        if (hasCloseButton) {
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bind(CLOSE_ICON);
            int closeX = x + width - 14;
            int closeY = y + 1;

            Color closeColor = color;
            if (GuiUtil.isMouseInRegion(mouseX, mouseY, closeX, closeY, 13, 13)) {
                closeColor = StyleManager.getCurrentStyle().getFGColor(true, true);
            }
            RenderSystem.color3f(closeColor.getRed() / 255f, closeColor.getGreen() / 255f, closeColor.getBlue() / 255f);
            AbstractGui.blit(matrix,
                    closeX, closeY,
                    0, 0,
                    13, 13,
                    16, 16
            );
            RenderSystem.color3f(1f, 1f, 1f);
        }

        if (children.size() == 0) {
            return;
        }

        int localX = x + 1;
        int localY = y + 1;
        if (showTitleBar) {
            localY += TITLEBAR_HEIGHT - 1;
        }
        int localMouseX = mouseX - localX;
        int localMouseY = mouseY - localY;

        RenderUtil.glScissorBox(localX, localY, localX + width - 1, localY + height - 1 - TITLEBAR_HEIGHT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glTranslated(localX, localY, 0d);
        for (Iterator<Widget> it = children.descendingIterator(); it.hasNext(); ) {
            it.next().render(matrix, localMouseX, localMouseY, partialTicks);
        }
        GL11.glTranslated(-localX, -localY, 0d);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void playDownSound(SoundHandler soundHandler) {
    }
}
