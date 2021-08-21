package creativeeditor.screen.widgets.base;

import creativeeditor.screen.FlagScreen;
import creativeeditor.screen.RawNBTEditorScreen;
import creativeeditor.screen.TagExplorerScreen;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.StyledTextButton;
import net.minecraft.client.resources.I18n;

public class AdvancedWidgets extends WidgetIteratorBase {

    public AdvancedWidgets() {
        add(new ClassSpecificWidget(I18n.get("gui.itemflag"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new FlagScreen(info.getParent(), item))))
        ));



        add(new ClassSpecificWidget(I18n.get("gui.rawnbt"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new RawNBTEditorScreen(info.getParent(), item))))
        ));



    }

}
