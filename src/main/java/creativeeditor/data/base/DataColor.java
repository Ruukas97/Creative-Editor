package creativeeditor.data.base;

import creativeeditor.data.Data;
import creativeeditor.util.ColorUtils.Color;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;

@SuppressWarnings( "serial" )
public class DataColor extends Color implements Data<Color, IntNBT> {
    @Getter
    @Setter
    private int defColor = 0;
    @Getter
    private float hue = 0;
    @Getter
    private float saturation = 0;
    @Getter
    private float brightness = 0;


    public DataColor(INBT nbt) {
        this( nbt instanceof IntNBT ? (IntNBT) nbt : new IntNBT( 0 ) );
    }


    public DataColor(IntNBT nbt) {
        this( nbt.getInt() );
    }


    public DataColor(int color) {
        super( color );
    }


    @Override
    public boolean isDefault() {
        return argb == defColor;
    }


    @Override
    public Color getData() {
        return this;
    }


    @Override
    public IntNBT getNBT() {
        return new IntNBT( argb );
    }


    private void updateHSB() {
        float[] hsb = getHSB();
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
    }


    public Color setHSB( float h, float s, float b ) {
        hue = h;
        saturation = s;
        brightness = b;
        return super.setInt( java.awt.Color.HSBtoRGB( h, s, b ) );
    }


    @Override
    public Color setInt( int color ) {
        super.setInt( color );
        updateHSB();
        return this;
    }
}
