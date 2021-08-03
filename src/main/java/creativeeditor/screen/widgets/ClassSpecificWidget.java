package creativeeditor.screen.widgets;

import creativeeditor.data.DataItem;
import net.minecraft.client.gui.widget.Widget;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ClassSpecificWidget {
    public final String text;
    public final Function<DataItem, Boolean> requirement;
    public final BiFunction<DataItem, WidgetInfo, Widget> widgetCreator;


    public ClassSpecificWidget(String text, Function<DataItem, Boolean> requirement, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        this.text = text;
        this.requirement = requirement;
        this.widgetCreator = widgetCreator;
    }

    public ClassSpecificWidget(String text, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        this.text = text;
        this.requirement =  dItem -> true;
        this.widgetCreator = widgetCreator;
    }


    @Nullable
    public Widget get(WidgetInfo info, DataItem item) {
        return requirement.apply(item) ? widgetCreator.apply(item, info) : null;
    }
}
