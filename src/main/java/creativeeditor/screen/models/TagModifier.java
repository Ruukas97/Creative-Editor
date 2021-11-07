package creativeeditor.screen.models;

import creativeeditor.data.Data;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;

public interface TagModifier<T extends Data<?, ?>> {
    void modify(T tag);

    Widget[] widgets(FontRenderer font, int width, int height);
}
