package infinityitemeditor.events;

import infinityitemeditor.screen.ParentItemScreen;
import infinityitemeditor.screen.widgets.ColorHelperWidget;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.EditBookScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.EditWorldScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenHandler {

    // Add ColorHelperWidget to GUI's
    @SubscribeEvent()
    public void onInitGui(ScreenEvent.InitScreenEvent.Post e) {
        if (e.getScreen() instanceof EditSignScreen || e.getScreen() instanceof EditWorldScreen || e.getScreen() instanceof EditBookScreen || e.getScreen() instanceof AddServerScreen) {
            e.addListener(new ColorHelperWidget(null, 156, 30, e.getScreen().width, e.getScreen().height));
        }
    }

    // Prioritize ColorHelperWidget
    @SubscribeEvent()
    public void onClick(ScreenEvent.MouseClickedEvent.Pre e) {
        if (e.getScreen() instanceof ParentItemScreen) return;
        for (GuiEventListener gel : e.getScreen().children()) {
            if (gel instanceof ColorHelperWidget) {
                ColorHelperWidget w = (ColorHelperWidget) gel;
                if (GuiUtil.isMouseInColorWidget((int) e.getMouseX(), (int) e.getMouseY(), w)) {
                    w.mouseClicked(e.getMouseX(), e.getMouseY(), e.getButton());
                    e.setCanceled(true);
                }
            }
        }
    }

}
