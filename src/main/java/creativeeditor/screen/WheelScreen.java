package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagAttributeModifier;
import creativeeditor.screen.models.TagFilter;
import creativeeditor.screen.models.TagModifier;
import creativeeditor.screen.models.WheelType;
import creativeeditor.screen.widgets.WidgetInfo;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.function.BiFunction;

public class WheelScreen<T extends Data<?, ?>> extends ParentItemScreen {
    protected final WheelType<T> wheelType;
    protected final TagFilter[] tagFilters;
    protected final TagModifier<T> tagModifier;
    protected final T[] tags;

    private int midX, midY, radius;
    private int rotOff = 0;
    private int mouseDist = 0;

    public WheelScreen(Screen lastScreen, TranslationTextComponent name, WheelType<T> wheelType, DataItem item) {
        super(name, lastScreen, item);
        this.wheelType = wheelType;
        tagFilters = wheelType.getTagFilters();
        tagModifier = wheelType.getTagModifier();
        tags = wheelType.getAll();
        renderItem = false;
    }

    @Override
    protected void init() {
        super.init();

        Widget[] widgets = tagModifier.widgets(font, width, height);
        for (Widget w : widgets) {
            addButton(w);
        }

        midX = width / 2;
        midY = height / 2;
        radius = height / 3;
    }

    @Override
    public void tick() {
        super.tick();
        if (Math.abs(mouseDist - radius) >= 16)
            rotOff++;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if(super.mouseClicked(mouseX, mouseY, mouseButton)){
            return true;
        }

        if (Math.abs(mouseDist - radius) < 16) {
            double angle = (2 * Math.PI) / tags.length;

            double closestDistance = Integer.MAX_VALUE;
            T closest = null;

            for (int i = 0; i < tags.length; i++) {
                double angleI = (((double) (rotOff) / 60d)) + (angle * i++);

                int x = (int) (midX + (radius * Math.cos(angleI)));
                int y = (int) (midY + (radius * Math.sin(angleI)));
                double distX = x - mouseX;
                double distY = y - mouseY;

                double dist = Math.sqrt(distX * distX + distY * distY);

                if (dist < 10 && dist < closestDistance) {
                    closestDistance = dist;
                    closest = tags[i];
                }
            }

            if (closest != null) {
                tagModifier.modify(closest);
                wheelType.addTag(closest);
                return true;
            }
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

        int distX = midX - mouseX;
        int distY = midY - mouseY;
        mouseDist = (int) Math.sqrt(distX * distX + distY * distY);

        double angle = 2 * Math.PI / tags.length;

        drawItemStack(matrix, item.getItemStack(), midX, midY, 5f, 0f, 0f, -rotOff * 3, null);

        for (int i = 0; i < tags.length; i++) {
            T tag = tags[i];
            tagModifier.modify(tag);
            double angleI = (((rotOff + (Math.abs(mouseDist - radius) >= 16 ? p3 : 0d)) / 60d)) + (angle * i++);
            int x = (int) (midX + (radius * Math.cos(angleI)));
            int y = (int) (midY + (radius * Math.sin(angleI)));


            RenderSystem.pushMatrix();
            RenderSystem.translated(0, 0, 300);
            drawCenteredString(matrix, font, wheelType.displayTag(tag), x, y - 17, color.getInt());
            RenderSystem.popMatrix();

            //drawItemStack(matrix, item.getItemStack(), x - 8, y - 8, 5f, 0f, 0f, 0f, null);

            fill(matrix, x - 1, y - 1, x + 1, y + 1, 0xFFFFFFFF);
        }

    }

    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }
}
