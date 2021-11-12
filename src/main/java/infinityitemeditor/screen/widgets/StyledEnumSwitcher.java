package infinityitemeditor.screen.widgets;

import infinityitemeditor.data.tag.TagEnum;

public class StyledEnumSwitcher extends StyledButton {
    public StyledEnumSwitcher(WidgetInfo info, TagEnum<?> data) {
        super(info.withTrigger(data));
        setMessage(data.getName());
    }

    public StyledEnumSwitcher(int x, int y, int width, int height, TagEnum<?> data) {
        super(x, y, width, height, data.get().name(), data);
        setMessage(data.getName());
    }
}
