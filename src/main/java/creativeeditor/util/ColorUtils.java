package creativeeditor.util;

import lombok.Getter;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn( Dist.CLIENT )
public class ColorUtils {
    public static Color multiplyColor( Color color, Color color2 ) {
        return new Color( MathHelper.multiplyColor( color.getInt(), color2.getInt() ) );
    }


    public static Color hsvToRGBInt( int hue, int saturation, int value ) {
        return new Color( MathHelper.hsvToRGB( hue / 255f, saturation / 255f, value / 255f ) ).setAlpha( 255 );
    }


    public static Color hsvToRGB( float hue, float saturation, float value ) {
        return new Color( MathHelper.hsvToRGB( hue, saturation, value ) ).setAlpha( 255 );
    }


    public static class Color {
        @Getter
        int alpha, red, green, blue;


        public Color() {
            this(255, 0, 0, 0);
        }


        public Color(int color) {
            alpha = (color & 0xFF000000) >> 24;
            red = (color & 0x00FF0000) >> 16;
            green = (color & 0x0000FF00) >> 8;
            blue = (color & 0x000000FF);
        }


        public Color(int a, int r, int g, int b) {
            setAlpha( a );
            setRed( r );
            setGreen( g );
            setBlue( b );
        }


        public int getInt() {
            return (alpha << 24) + (red << 16) + (green << 8) + blue;
        }


        public Color setAlpha( int alpha ) {
            this.alpha = alpha & 0xFF;
            return this;
        }


        public void setRed( int red ) {
            this.red = red & 0xFF;
        }


        public void setGreen( int green ) {
            this.green = green & 0xFF;
        }


        public void setBlue( int blue ) {
            this.blue = blue & 0xFF;
        }


        @Override
        public String toString() {
            return String.format( "Color: r:%f, g:%f, b:%f", getRed(), getGreen(), getBlue() );
        }
    }
}
