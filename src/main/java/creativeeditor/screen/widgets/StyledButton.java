package creativeeditor.screen.widgets;

import creativeeditor.styles.IStyledButton;
import creativeeditor.styles.StyleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;

public class StyledButton extends Button implements IStyledButton {
    public StyledButton(int x, int y, int width, int height, String text, IPressable onPress) {
        super( x, y, width, height, text, onPress );
    }


    @Override
    public void renderButton( int mouseX, int mouseY, float unused ) {
        StyleManager.getCurrentStyle().renderButton( this, mouseX, mouseY, alpha );
    }


    @Override
    public void renderBg( Minecraft mc, int mouseX, int mouseY ) {
        super.renderBg( mc, mouseX, mouseY );
    }


    @Override
    public int getYImage( boolean p_getYImage_1_ ) {
        return super.getYImage( p_getYImage_1_ );
    }


    @Override
    public int getFGColor() {
        return StyleManager.getCurrentStyle().getFGColor( this ).getInt();
    }


    @Override
    public Widget getWidget() {
        return this;
    }


    @Override
    public void setHovered( boolean b ) {
        isHovered = b;
    }
}
