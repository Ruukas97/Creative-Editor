package infinityitemeditor.screen.models;

import infinityitemeditor.data.Data;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;

public interface TagModifier<T extends Data<?, ?>> {
    void modify(T tag);

    Widget[] widgets(Font font, int width, int height);
}
