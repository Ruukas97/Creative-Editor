package infinityitemeditor.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.ClassSpecificWidget;
import infinityitemeditor.screen.widgets.ScrollableScissorWindow;
import infinityitemeditor.screen.widgets.WidgetInfo;
import infinityitemeditor.screen.widgets.base.AdvancedWidgets;
import infinityitemeditor.screen.widgets.base.ItemWidgets;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;

public class AllWidgetsScreen extends ParentItemScreen {

    ScrollableScissorWindow scissorWindow;

    public AllWidgetsScreen(Screen lastScreen, DataItem editing) {
        super(new TranslatableComponent("gui.allwidgets"), lastScreen, editing);
    }

    @Override
    protected void init() {
        renderItem = false;
        super.init();
        scissorWindow = addRenderableWidget(new ScrollableScissorWindow(width / 5, height / 10, width / 5 * 3, resetButton.y - height / 10 - 10, new TranslatableComponent("gui.allwidgets")));
        ArrayList<ClassSpecificWidget> list = new ArrayList<>();
        list.addAll(new ItemWidgets().list);
        list.addAll(new AdvancedWidgets().list);
        int y = scissorWindow.y + 10;
        for (int i = 0; i < list.size(); i++) {
            ClassSpecificWidget csw = list.get(i);
            if (csw.text.equalsIgnoreCase(I18n.get("gui.allwidgets")))
                return; // maybe find an alternative method for this?
            WidgetInfo widgetInfo = new WidgetInfo(scissorWindow.x + 10, y, 10, 10, csw.text, null, this, font);
            Widget w = csw.getRaw(widgetInfo, item);
            scissorWindow.getWidgets().add(w);
            if (i % 2 == 0) y += 10;
        }
        setInitialFocus(scissorWindow);

    }

    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(poseStack,mouseX, mouseY, p3, color);

    }
}
