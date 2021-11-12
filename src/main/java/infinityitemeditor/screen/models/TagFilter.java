package infinityitemeditor.screen.models;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.StyledOptionSwitcher;
import net.minecraft.util.text.TextComponent;

public interface TagFilter<T extends Data<?, ?>> extends StyledOptionSwitcher.Option {
    TextComponent getName();

    boolean shouldShow(DataItem item, T tag);

    T[] filter(DataItem item, T[] tags);
}
