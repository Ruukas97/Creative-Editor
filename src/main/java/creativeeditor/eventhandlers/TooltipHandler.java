package creativeeditor.eventhandlers;

import creativeeditor.screen.ParentScreen;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TooltipHandler {
    @SubscribeEvent
    public void onTooltip( RenderTooltipEvent.Color e ) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.currentScreen instanceof ParentScreen))
            StyleManager.getCurrentStyle().update();

        // e.setBackground( new ColorUtils.Color( 200, 55, 55, 55 ).getInt() );
        // e.setBackground( new ColorUtils.Color( 200, 55, 55, 55 ).getInt() );
        Color color1 = new Color( StyleManager.getCurrentStyle().getMainColor().getInt() );
        //color1.setRed( color1.getRed()/2 );
        //color1.setGreen( color1.getGreen()/2 );
        //color1.setBlue( color1.getBlue()/2 );
        Color color2 = StyleManager.getCurrentStyle().getFGColor( false, false );
        //color2.setRed( color2.getRed()/2 );
        //color2.setGreen( color2.getGreen()/2 );
        //color2.setBlue( color2.getBlue()/2 );
        e.setBorderStart( color1.getInt() );
        e.setBorderEnd( color2.getInt() );

        //System.out.println( (color2.getRed()/2) & 0xFF );
        //e.setBackground( c / 2 );
    }
}
