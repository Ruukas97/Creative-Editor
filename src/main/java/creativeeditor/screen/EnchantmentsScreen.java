package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class EnchantmentsScreen extends ParentItemScreen {

    public EnchantmentsScreen(Screen lastScreen, DataItem editing) {
        super( new TranslationTextComponent( "gui.enchantment" ), lastScreen, editing );
    }


    @Override
    protected void init() {
        super.init();
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        super.backRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        super.mainRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color color ) {
        super.overlayRender( mouseX, mouseY, p3, color );
    }
}
