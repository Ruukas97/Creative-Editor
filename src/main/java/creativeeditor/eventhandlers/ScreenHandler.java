package creativeeditor.eventhandlers;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.ALUtil;

import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenHandler {

    List<String> devices = new ArrayList<>();


    @SubscribeEvent( )
    public void onInitGui( GuiScreenEvent.InitGuiEvent e ) {
        /*
         * if (e.getGui() instanceof CreativeScreen) { CreativeScreen creative =
         * (CreativeScreen)e.getGui(); e.addWidget(new
         * StyledButton(creative.getGuiLeft(), creative.getGuiTop(), 30, 20,
         * I18n.format("creativeeditor.switchgamemode"), b ->
         * PlayerUtils.switchGamemode())); } else
         */
        if (e.getGui() instanceof OptionsSoundsScreen) {
            int i = 11;
            e.addWidget( new Button( e.getGui().width / 2 - 155 + i % 2 * 160, e.getGui().height / 6 - 12 + 24 * (i >> 1), 150, 20, I18n.format( "gui.optionsound" ), ( Button t ) -> {
                devices = ALUtil.getStringList( 0L, 4115 );
                for (String s : devices) {
                    System.out.println( s );
                }
            } ) );
        }
    }

}
