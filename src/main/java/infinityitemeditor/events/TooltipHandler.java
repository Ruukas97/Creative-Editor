package infinityitemeditor.events;

import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.styles.StyleVanilla;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TooltipHandler {
    @SubscribeEvent
    public void onTooltip(RenderTooltipEvent.Color e) {
        //TODO custom nbt tooltip color

        if (StyleManager.getCurrentStyle() instanceof StyleVanilla) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof ParentScreen))
            StyleManager.getCurrentStyle().update();

        Color color1 = new Color(StyleManager.getCurrentStyle().getMainColor().getInt());
        color1.setValue(.7f).setAlpha(230);
        e.setBorderStart(color1.getInt());

        Color color2 = StyleManager.getCurrentStyle().getFGColor(false, false);
        e.setBorderEnd(color2.getInt());

        color1.setValue(.125f).setAlpha(210);
        e.setBackground(color1.getInt());
    }
}
