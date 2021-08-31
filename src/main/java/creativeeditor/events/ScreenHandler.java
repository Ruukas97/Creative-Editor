package creativeeditor.events;

import creativeeditor.screen.ParentItemScreen;
import creativeeditor.screen.widgets.ColorHelperWidget;
import creativeeditor.util.GuiUtil;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.EditBookScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.EditWorldScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenHandler {

    // Add ColorHelperWidget to GUI's
    @SubscribeEvent()
    public void onInitGui(GuiScreenEvent.InitGuiEvent e) {
        if (e.getGui() instanceof EditSignScreen || e.getGui() instanceof EditWorldScreen || e.getGui() instanceof EditBookScreen || e.getGui() instanceof AddServerScreen) {
            e.addWidget(new ColorHelperWidget(null, 156, 30, e.getGui().width, e.getGui().height));
        }
    }

    // Prioritize ColorHelperWidget
    @SubscribeEvent()
    public void onClick(GuiScreenEvent.MouseClickedEvent.Pre e) {
        if (e.getGui() instanceof ParentItemScreen) return;
        for (IGuiEventListener gel : e.getGui().children()) {
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
