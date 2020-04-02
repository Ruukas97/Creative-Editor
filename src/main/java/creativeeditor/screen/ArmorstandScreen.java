package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class ArmorstandScreen extends ParentItemScreen {

    public ArmorstandScreen(Screen lastScreen, DataItem item) {
        super( new TranslationTextComponent( "gui.armorstand" ), lastScreen, item );
    }


    @Override
    protected void init() {
        super.init();
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {

        super.backRender( mouseX, mouseY, p3, color );
    }


}
