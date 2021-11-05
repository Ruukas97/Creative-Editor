package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.screen.models.TagFilter;
import creativeeditor.screen.models.TagModifier;
import creativeeditor.screen.models.WheelType;
import creativeeditor.screen.widgets.WidgetInfo;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.function.BiFunction;

public class WheelScreen<T extends Data<?,?>> extends ParentItemScreen {
    protected final WheelType<T> wheelType;
    protected final TagFilter[] tagFilters;
    protected final TagModifier<T> tagModifier;
    protected final T[] tags;

    private int rotOff = 0;
    private int mouseDist = 0;

    public WheelScreen(Screen lastScreen, TranslationTextComponent name,  WheelType<T> wheelType, DataItem item) {
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
        for(Widget w : widgets){
            addButton(w);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);
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
