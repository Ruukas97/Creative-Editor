package infinityitemeditor.data.base;

import infinityitemeditor.data.Data;
import infinityitemeditor.util.ColorUtils.Color;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;

@SuppressWarnings("serial")
public class DataColor extends Color implements Data<Color, IntTag> {
    @Getter
    @Setter
    private int defColor = 0;
    @Getter
    private float hue = 0;
    @Getter
    private float saturation = 0;
    @Getter
    private float brightness = 0;


    public DataColor(Tag nbt) {
        this(nbt instanceof IntTag ? (IntTag) nbt : IntTag.valueOf(0));
    }


    public DataColor(IntTag nbt) {
        this(nbt.getAsInt());
    }


    public DataColor(int color) {
        super(color);
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
    public IntTag getTag() {
        return IntTag.valueOf(argb);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent itextcomponent = (new TextComponent(getHexString().substring(1))).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//        return (new TextComponent("#").append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE));
//    }


    private void updateHSB() {
        float[] hsb = getHSB();
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
    }


    public Color setHSB(float h, float s, float b) {
        hue = h;
        saturation = s;
        brightness = b;
        return super.setInt(java.awt.Color.HSBtoRGB(h, s, b));
    }


    @Override
    public Color setInt(int color) {
        super.setInt(color);
        updateHSB();
        return this;
    }
}
