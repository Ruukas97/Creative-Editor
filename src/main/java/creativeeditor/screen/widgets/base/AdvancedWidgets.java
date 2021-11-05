package creativeeditor.screen.widgets.base;

import creativeeditor.screen.AllWidgetsScreen;
import creativeeditor.screen.RawNBTEditorScreen;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.StyledTextButton;
import net.minecraft.client.resources.I18n;

public class AdvancedWidgets extends WidgetIteratorBase {

    public AdvancedWidgets() {
        add(new ClassSpecificWidget(I18n.get("gui.allwidgets"), true, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new AllWidgetsScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.rawnbt"), true, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new RawNBTEditorScreen(info.getParent(), item))))
        ));
    }
}
