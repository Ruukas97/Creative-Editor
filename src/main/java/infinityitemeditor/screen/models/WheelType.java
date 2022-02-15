package infinityitemeditor.screen.models;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.util.TextComponentUtils;
import lombok.Getter;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.text.MutableComponent;
import net.minecraft.util.text.TextComponent;

import java.util.Comparator;

public abstract class WheelType<T extends Data<?, ?>> implements Comparator<T> {
    public static final TagFilter<?> ALL = new TagFilter<Data<?, ?>>() {
        @Override
        public TextComponent getName() {
            return new TranslatableComponent("filter.all");
        }

        @Override
        public boolean shouldShow(DataItem item, Data<?, ?> tag) {
            return true;
        }

        @Override
        public Data<?, ?>[] filter(DataItem item, Data<?, ?>[] tags) {
            return tags;
        }
    };


    protected DataItem dataItem;

    @Getter
    TagFilter<?>[] tagFilters;

    @SafeVarargs
    public WheelType(DataItem dataItem, TagFilter<T>... tagFilters) {
        this.dataItem = dataItem;
        this.tagFilters = new TagFilter[tagFilters.length + 1];
        this.tagFilters[0] = ALL;
        System.arraycopy(tagFilters, 0, this.tagFilters, 1, tagFilters.length);
    }

    public abstract TagModifier<T> getTagModifier();

    public abstract T[] newArray(int size);

    public abstract T clone(T tag);

    public abstract T[] getAll();

    public abstract TextComponent displayTag(T tag);

    public abstract MutableComponent[] tooltip(T tag);

    @Override
    public int compare(T o1, T o2) {
        return TextComponentUtils.getPlainText(displayTag(o1)).compareTo(TextComponentUtils.getPlainText(displayTag(o2)));
    }
}
