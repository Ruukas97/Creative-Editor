package creativeeditor.widgets;

import net.minecraft.client.gui.widget.Widget;

public interface WidgetInfoSupport<T extends Widget> {
    public T fromWidgetInfo(WidgetInfo info);
}
