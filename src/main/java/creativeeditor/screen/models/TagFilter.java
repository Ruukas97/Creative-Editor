package creativeeditor.screen.models;

import creativeeditor.data.Data;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface TagFilter<T extends Data<?, ?>> {
    ITextComponent getName();

    boolean shouldShow(T tag);

    T[] filter(T[] tags);
}
