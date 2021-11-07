package creativeeditor.screen.models;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import lombok.Getter;
import net.minecraft.util.text.TextComponent;

public abstract class WheelType<T extends Data<?, ?>> {
    protected DataItem dataItem;

    @Getter
    TagFilter<T>[] tagFilters;

    @SafeVarargs
    public WheelType(DataItem dataItem, TagFilter<T>... tagFilters) {
        this.dataItem = dataItem;
        this.tagFilters = tagFilters;
    }

    public abstract TagModifier<T> getTagModifier();

    public abstract T[] newArray(int size);

    public abstract T[] getAll();

    public abstract TextComponent displayTag(T tag);
}
