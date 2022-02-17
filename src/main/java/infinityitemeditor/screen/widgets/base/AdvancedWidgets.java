package infinityitemeditor.screen.widgets.base;

import infinityitemeditor.screen.AllWidgetsScreen;
import infinityitemeditor.screen.nbt.NBTEditorScreen;
import infinityitemeditor.screen.RawNBTEditorScreen;
import infinityitemeditor.screen.widgets.ClassSpecificWidget;
import infinityitemeditor.screen.widgets.StyledTextButton;
import net.minecraft.client.resources.I18n;

public class AdvancedWidgets extends WidgetIteratorBase {

    public AdvancedWidgets() {
        add(new ClassSpecificWidget(I18n.get("gui.allwidgets"), true, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new AllWidgetsScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.nbt"), true, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new NBTEditorScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.rawnbt"), true, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new RawNBTEditorScreen(info.getParent(), item))))
        ));
    }
}
