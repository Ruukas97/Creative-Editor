package creativeeditor.screen.widgets;

import net.minecraft.client.gui.widget.Widget;

public interface WidgetInfoSupport<T extends Widget> {
    T fromWidgetInfo(WidgetInfo info);
}
