package creativeeditor.screen.widgets;

import creativeeditor.data.DataItem;
import net.minecraft.client.gui.widget.Widget;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ClassSpecificWidget implements Comparable<ClassSpecificWidget> {
    public final String text;
    public final Function<DataItem, Boolean> requirement;
    public final BiFunction<DataItem, WidgetInfo, Widget> widgetCreator;


    public ClassSpecificWidget(String text, Function<DataItem, Boolean> requirement, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        this.text = text;
        this.requirement = requirement;
        this.widgetCreator = widgetCreator;
    }

    public ClassSpecificWidget(String text, boolean allowAir, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        this(text, dItem -> allowAir, widgetCreator);
    }

    public ClassSpecificWidget(String text, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        this(text, dItem -> true, widgetCreator);
    }


    @Nullable
    public Widget get(WidgetInfo info, DataItem item) {
        return requirement.apply(item) ? widgetCreator.apply(item, info) : null;
    }

    public Widget getRaw(WidgetInfo info, DataItem item) {
        return widgetCreator.apply(item, info);
    }

    @Override
    public int compareTo(ClassSpecificWidget o) {
        return this.text.compareTo(o.text);
    }
}
