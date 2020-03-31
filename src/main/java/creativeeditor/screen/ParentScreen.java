package creativeeditor.screen;

import java.util.List;

import com.google.common.collect.Lists;

import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.widgets.StyledTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

public abstract class ParentScreen extends Screen {
    protected final Screen lastScreen;
    protected Minecraft mc;
    protected FontRenderer fontRenderer;
    protected List<Widget> renderWidgets = Lists.newArrayList();


    public ParentScreen(ITextComponent title, Screen lastScreen) {
        super( title );
        this.lastScreen = lastScreen;
        this.mc = Minecraft.getInstance();
        this.fontRenderer = mc.fontRenderer;
    }


    @Override
    protected void init() {
    }


    @Override
    public void resize( Minecraft mc, int width, int height ) {
        super.resize( mc, width, height );
    }


    @Override
    public void onClose() {        
        this.minecraft.displayGuiScreen(lastScreen);
    }


    @Override
    public boolean keyPressed( int key1, int key2, int key3 ) {
        return super.keyPressed( key1, key2, key3 );
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }


    public int getBlitOffset() {
        return blitOffset;
    }


    @Override
    public void tick() {
        renderWidgets.forEach( w -> {
            if (w instanceof StyledTextField) {
                ((StyledTextField) w).tick();
            }
        } );
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        for(Widget w : renderWidgets) {
            if (w.mouseClicked( mouseX, mouseY, mouseButton )) {
                this.setFocused( w );
                if (mouseButton == 0) {
                    this.setDragging( true );
                }

                return true;
            }
        }
        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    @Deprecated
    public void render( int mouseX, int mouseY, float p3 ) {
        Color color = StyleManager.getCurrentStyle().getMainColor();
        backRender( mouseX, mouseY, p3, color );
        mainRender( mouseX, mouseY, p3, color );
        overlayRender( mouseX, mouseY, p3, color );
        StyleManager.getCurrentStyle().update();
    }


    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        StyleManager.getCurrentStyle().renderBackground( this );

        // Frame
        GuiUtil.drawFrame( 5, 5, width - 5, height - 5, 1, color );

        // GUI Title
        drawCenteredString( font, getTitle().getFormattedText(), width / 2, 9, color.getInt() );
        int sWidthHalf = font.getStringWidth( getTitle().getFormattedText() ) / 2 + 3;

        // Title underline
        AbstractGui.fill( width / 2 - sWidthHalf, 20, width / 2 + sWidthHalf, 21, color.getInt() );
    }


    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        buttons.forEach( b -> b.render( mouseX, mouseY, p3 ) );
        renderWidgets.forEach( w -> w.render( mouseX, mouseY, p3 ) );
    }


    /**
     * Should always be called last in render, but only once.
     */
    public void overlayRender( int mouseX, int mouseY, float p3, Color color ) {
    }
}
