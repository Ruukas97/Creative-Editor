package creativeeditor.screen.models;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.StyledOptionSwitcher;
import net.minecraft.util.text.TextComponent;

public interface TagFilter<T extends Data<?, ?>> extends StyledOptionSwitcher.Option {
    TextComponent getName();

    boolean shouldShow(DataItem item, T tag);

    T[] filter(DataItem item, T[] tags);
}
