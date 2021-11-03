package creativeeditor.screen;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagList;
import creativeeditor.screen.models.TagFilter;
import creativeeditor.screen.models.TagModifier;
import creativeeditor.screen.widgets.WidgetInfo;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.function.BiFunction;

public class WheelScreen<T extends Data<?,?>> extends ParentItemScreen {
    protected TagList<T> tagList;
    protected List<TagFilter<T>> filters;
    protected TagModifier<T> modifier;


    private int rotOff = 0;
    private int mouseDist = 0;

    public WheelScreen(Screen lastScreen, TranslationTextComponent name, TagModifier<T> modifier, DataItem item) {
        super(name, lastScreen, item);
        this.modifier = modifier;
        renderItem = false;
    }

    @Override
    protected void init() {
        super.init();

        List<BiFunction<DataItem, WidgetInfo, Widget>> widgets = modifier.widgets();

        int x = 20;
        int y = height - 30;
        for(BiFunction<DataItem, WidgetInfo, Widget> widget : widgets){
            addButton(widget.apply(item, new WidgetInfo(x, y, 100, 20, "", null, this, font)));
            y -= 30;
        }
    }
}
