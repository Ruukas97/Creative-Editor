package creativeeditor.screen.widgets;

import creativeeditor.data.base.DataEnum;

public class StyledEnumSwitcher extends StyledButton {
    public StyledEnumSwitcher(WidgetInfo info, DataEnum<?> data) {
        super(info.withTrigger(data));
        setMessage(data.getName());
    }

    public StyledEnumSwitcher(int x, int y, int width, int height, DataEnum<?> data) {
        super(x, y, width, height, data.get().name(), data);
        setMessage(data.getName());
    }
}
