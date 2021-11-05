package creativeeditor.screen.models;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.WidgetInfo;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.widget.Widget;

import java.util.List;
import java.util.function.BiFunction;

public interface TagModifier<T extends Data<?, ?>> {
    void modify(T tag);
    List<BiFunction<DataItem, WidgetInfo, Widget>> widgets();
}
