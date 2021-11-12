package infinityitemeditor.util;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColorUtils {
    @OnlyIn(Dist.CLIENT)
    public static int multiplyColor(int firstColor, int secondColor) {
        int i = (firstColor & 16711680) >> 16;
        int j = (secondColor & 16711680) >> 16;
        int k = (firstColor & '\uff00') >> 8;
        int l = (secondColor & '\uff00') >> 8;
        int i1 = (firstColor & 255) >> 0;
        int j1 = (secondColor & 255) >> 0;
        int k1 = (int) ((float) i * (float) j / 255.0F);
        int l1 = (int) ((float) k * (float) l / 255.0F);
        int i2 = (int) ((float) i1 * (float) j1 / 255.0F);
        return firstColor & -16777216 | k1 << 16 | l1 << 8 | i2;
    }


    public static int mcHSVtoRGB(float hue, float saturation, float value) {
        return MathHelper.hsvToRgb(hue, saturation, value);
    }


    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        return java.awt.Color.HSBtoRGB(hue, saturation, brightness);
    }


    @SuppressWarnings("serial")
    public static class Color extends Number implements Comparable<Integer> {
        protected int argb;


        public Color(int color) {
            setInt(color);
        }


        public Color(int r, int g, int b) {
            setInt(0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF);
        }


        public Color(int a, int r, int g, int b) {
            setARGB(a, r, g, b);
        }


        public static Color fromHSV(float hue, float saturation, float value) {
            return new Color(mcHSVtoRGB(hue, saturation, value));
        }


        public static Color fromHSB(float hue, float saturation, float brightness) {
            return new Color(HSBtoRGB(hue, saturation, brightness));
        }


        public Color setRGB(int r, int g, int b) {
            return setInt(argb & 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF);
        }


        public Color setARGB(int a, int r, int g, int b) {
            return setInt((a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF);
        }


        public int getAlpha() {
            return argb >> 24 & 0xFF;
        }


        public Color setAlpha(int alpha) {
            return setInt((alpha & 0xFF) << 24 | argb & 0x00FFFFFF);
        }


        public int getRed() {
            return argb >> 16 & 0xFF;
        }


        public Color setRed(int red) {
            return setInt((red & 0xFF) << 16 | argb & 0xFF00FFFF);
        }


        public int getGreen() {
            return (argb >> 8) & 0xFF;
        }


        public Color setGreen(int green) {
            return setInt((green & 0xFF) << 8 | (argb & 0xFFFF00FF));
        }


        public int getBlue() {
            return argb & 0xFF;
        }


        public Color setBlue(int blue) {
            return setInt((blue & 0xFF) | (argb & 0xFFFFFF00));
        }


        public int getInt() {
            return argb;
        }


        public Color setInt(int color) {
            argb = color;
            return this;
        }


        public Color hueShift() {
            int r = getRed();
            int g = getGreen();
            int b = getBlue();
            if (r == g && r == b) {
                return this;
            }
            int min = Math.min(r, Math.min(g, b));
            int max = Math.max(r, Math.max(g, b));

            if (r == max && g != max) {
                if (b == min) {
                    setGreen(g + 1);
                } else {
                    setBlue(b - 1);
                }
            } else if (g == max && b != max) {
                if (r == min) {
                    setBlue(b + 1);
                } else {
                    setRed(r - 1);
                }
            } else if (b == max && r != max) {
                if (g == min) {
                    setRed(r + 1);
                } else {
                    setGreen(g - 1);
                }
            }
            return this;
        }


        public int getPossibleHueShifts() {
            int r = getRed();
            int g = getGreen();
            int b = getBlue();
            int min = Math.min(r, Math.min(g, b));
            int max = Math.max(r, Math.max(g, b));
            return max - min;
        }


        public float getHue() {
            float r = getRed();
            float g = getGreen();
            float b = getBlue();
            float min = Math.min(r, Math.min(g, b));
            float max = Math.max(r, Math.max(g, b));
            if (min == max) {
                return 0f;
            }
            float res;
            if (r == max)
                res = (g - b) / (r - min);
            else if (g == max)
                res = 2f + (b - r) / (g - min);
            else
                res = 4f + (r - g) / (b - min);
            res = res / 6;
            return res < 0 ? 1 + res : res;
        }


        public float[] getHSB() {
            return java.awt.Color.RGBtoHSB(getRed(), getGreen(), getBlue(), null);
        }


        public float getHSBSaturation() {
            return getHSB()[1];
        }


        public float getHSVSaturation() {
            float r = getRed() / 255f;
            float g = getGreen() / 255f;
            float b = getBlue() / 255f;
            float min = Math.min(r, Math.min(g, b));
            float max = Math.max(r, Math.max(g, b));
            float delta = max - min;
            return delta == 0f ? 0f : 1f - Math.abs(min + max - 1f);
        }


        public float getLightness() {
            float r = getRed();
            float g = getGreen();
            float b = getBlue();
            float min = Math.min(r, Math.min(g, b));
            float max = Math.max(r, Math.max(g, b));
            return (min + max) / 510f;
        }


        public float getValue() {
            float r = getRed();
            float g = getGreen();
            float b = getBlue();
            float max = Math.max(r, Math.max(g, b));
            return max / 255f;
        }


        public Color setValue(float value) {
            float r = getRed();
            float g = getGreen();
            float b = getBlue();
            float max = Math.max(r, Math.max(g, b));
            float v = max / 255f;
            float f = value / v;
            setRed((int) (r * f));
            setGreen((int) (g * f));
            setBlue((int) (b * f));
            return this;
        }


        public String getHexString() {
            return String.format("#%06X", (getInt() & 0xFFFFFF));
        }


        public String getHexStringWithAlpha() {
            return String.format("#%08X", (getInt()));
        }


        public Color copy() {
            return new Color(argb);
        }


        @Override
        public int compareTo(Integer o) {
            return Integer.compare(getInt(), o);
        }


        @Override
        public int intValue() {
            return getInt();
        }


        @Override
        public long longValue() {
            return getInt();
        }


        @Override
        public float floatValue() {
            return (float) getInt();
        }


        @Override
        public double doubleValue() {
            return getInt();
        }


        @Override
        public String toString() {
            return "Color[A:" + getAlpha() + ",R:" + getRed() + ",G:" + getGreen() + ",B:" + getBlue() + "]";
        }

        public net.minecraft.util.text.Color toMcColor() {
            return net.minecraft.util.text.Color.fromRgb(argb);
        }
    }
}
