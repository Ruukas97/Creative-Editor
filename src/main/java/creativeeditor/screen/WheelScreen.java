package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagList;
import creativeeditor.screen.models.TagFilter;
import creativeeditor.screen.models.TagModifier;
import creativeeditor.screen.models.WheelType;
import creativeeditor.screen.widgets.ScrollableScissorWindow;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils;
import creativeeditor.util.TextComponentUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class WheelScreen<T extends Data<?, ?>> extends ParentItemScreen {
    protected final WheelType<T> wheelType;
    protected final TagFilter<T>[] tagFilters;
    protected final TagModifier<T> tagModifier;
    protected final T[] tags;
    protected final TagList<T> added;
    protected T hovered = null;

    private ScrollableScissorWindow addedWidgets;

    private int midX, midY;
    private double radius, radiusSquared;
    private int rotOff = 0;
    private int mouseDistSquared = 0;

    public WheelScreen(Screen lastScreen, TranslationTextComponent name, WheelType<T> wheelType, DataItem item, TagList<T> list) {
        super(name, lastScreen, item);
        this.wheelType = wheelType;
        tagFilters = wheelType.getTagFilters();
        tagModifier = wheelType.getTagModifier();
        tags = wheelType.getAll();
        added = list;
        renderItem = false;
    }

    @Override
    protected void init() {
        super.init();

        Widget[] widgets = tagModifier.widgets(font, width, height);
        for (Widget w : widgets) {
            addButton(w);
        }

        addedWidgets = addButton(new ScrollableScissorWindow(width - 130, 50, 120, height - 60, new TranslationTextComponent("gui.attributemodifiers.added")));

        midX = width / 2;
        midY = height / 2;
        radius = height / 3d;
        radiusSquared = radius * radius;
    }

    @Override
    public void tick() {
        super.tick();
        if (Math.abs(mouseDistSquared - radiusSquared) >= 3000) {
            rotOff++;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }

        if (hovered != null) {
            T clicked = hovered;
            tagModifier.modify(clicked);
            added.add(clicked);
            addedWidgets.getWidgets().add(new StyledButton(0, 0, 0, 20, TextComponentUtils.getPlainText(wheelType.displayTag(clicked)), (button) -> {
                addedWidgets.getWidgets().remove(button);
                added.remove(clicked);
            }));
            return true;
        }

        return false;
    }

    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        hovered = null;

        int midDistX = midX - mouseX;
        int midDistY = midY - mouseY;
        mouseDistSquared = midDistX * midDistX + midDistY * midDistY;

        double angle = 2 * Math.PI / tags.length;
        double closestDistanceSquared = Integer.MAX_VALUE;
        boolean hovering = Math.abs(mouseDistSquared - radiusSquared) < 3000;

        drawString(matrix, font, addedWidgets.getMessage(), addedWidgets.x, addedWidgets.y - 10, color.getInt());


        //drawItemStack(matrix, item.getItemStack(), midX, midY, 5f, 0f, 0f, -rotOff * 3, null);

        for (int i = 0; i < tags.length; i++) {
            T tag = tags[i];
            tagModifier.modify(tag);
            double angleI = (((rotOff + (hovering ? 0d : p3)) / 60d)) + (angle * i);
            double x = midX + (radius * Math.cos(angleI));
            double y = midY + (radius * Math.sin(angleI));

            if (hovering && hovered == null) {
                double distX = x - mouseX;
                double distY = y - mouseY;
                double distSquared = distX * distX + distY * distY;

                if (distSquared < 100 && distSquared < closestDistanceSquared) {
                    closestDistanceSquared = distSquared;
                    hovered = tags[i];
                }
            }

            RenderSystem.pushMatrix();
            RenderSystem.translated(x, y, 300);
            drawCenteredString(matrix, font, wheelType.displayTag(tag).withStyle(Style.EMPTY.withColor(StyleManager.getCurrentStyle().getFGColor(true, tag == hovered).toMcColor())), 0, -17, color.getInt());
            fill(matrix, -1, -1, 1, 1, color.getInt());
            RenderSystem.translated(0, 0, -300);
            drawItemStack(item.getItemStack(), -8, -8, 0f, 0f, null);
            RenderSystem.popMatrix();
        }
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }
}
