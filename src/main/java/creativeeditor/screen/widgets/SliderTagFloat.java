package creativeeditor.screen.widgets;

import org.lwjgl.glfw.GLFW;

import creativeeditor.data.NumberRangeFloat;
import creativeeditor.styles.IStyledSlider;
import creativeeditor.styles.StyleManager;
import creativeeditor.styles.StyleSpectrum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn( Dist.CLIENT )
public class SliderTagFloat extends Widget implements IStyledSlider<Float> {
    private NumberRangeFloat range;

    public String display;
    public boolean drawString;


    public SliderTagFloat(int x, int y, int width, int height, String display, NumberRangeFloat range) {
        this( x, y, width, height, display, true, range );
    }


    public SliderTagFloat(int x, int y, int width, int height, NumberRangeFloat range) {
        this( x, y, width, height, "", true, range );
    }


    public SliderTagFloat(int x, int y, int width, int height, String display, boolean drawString, NumberRangeFloat range) {
        super( x, y, width, height, display );
        this.display = display;
        this.drawString = drawString;
        this.range = range;

        setMessage( drawString ? display + range.get() : "" );
    }


    @Override
    public int getYImage( boolean b ) {
        return 0;
    }


    @Override
    protected String getNarrationMessage() {
        return I18n.format( "gui.narrate.slider", getMessage() );
    }


    @Override
    public void renderButton( int mouseX, int mouseY, float p3 ) {
        // super.renderButton(mouseX, mouseY, p3);
        StyleManager.getCurrentStyle().renderButton( this, mouseX, mouseY, p3 );
    }


    public void renderBg( Minecraft mc, int mouseX, int mouseY ) {
        if (!this.visible)
            return;

        StyleManager.getCurrentStyle().renderSlider( this, mouseX, mouseY );
    }


    private void setValueFromMouse( double mouseX ) {
        float updateValue;
        if (StyleManager.getCurrentStyle() instanceof StyleSpectrum)
            updateValue = (((float) mouseX - (x + 1f)) / (float) (width - 2.5f));
        else
            updateValue = ((float) mouseX - (x + 4)) / (float) (width - 8);
        float round = updateValue * (range.getMax() - range.getMin()) + range.getMin();
        setValue( round );
    }


    @Override
    public void onClick( double mouseX, double mouseY ) {
        setValueFromMouse( mouseX );
    }


    @Override
    public boolean keyPressed( int key1, int key2, int key3 ) {
        if (key1 == GLFW.GLFW_KEY_LEFT && getValue() > getMin()) {
            range.set( range.get() - 1 );
            updateSlider();
            return true;
        }

        if (key1 == GLFW.GLFW_KEY_RIGHT && getValue() < getMax()) {
            range.set( range.get() + 1 );
            updateSlider();
            return true;
        }

        return false;
    }


    public void setValue( float value ) {
        float old = getValue();
        range.set( value );
        if (getValue() != old) {
            updateSlider();
        }
    }


    public void updateSlider() {
        setMessage( drawString ? display + getValue() : "" );
    }


    @Override
    protected void onDrag( double mouseX, double p_onDrag_3_, double p_onDrag_5_, double p_onDrag_7_ ) {
        setValueFromMouse( mouseX );
        super.onDrag( mouseX, p_onDrag_3_, p_onDrag_5_, p_onDrag_7_ );
    }


    @Override
    public void playDownSound( SoundHandler soundHandler ) {
    }


    @Override
    public void onRelease( double mouseX, double mouseY ) {
        super.playDownSound( Minecraft.getInstance().getSoundHandler() );
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


    @Override
    public Float getValue() {
        return range.get();
    }


    @Override
    public Float getMin() {
        return range.getMin();
    }


    @Override
    public Float getMax() {
        return range.getMax();
    }
}
