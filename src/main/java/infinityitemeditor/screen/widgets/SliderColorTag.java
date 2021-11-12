package infinityitemeditor.screen.widgets;

import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.DataColor;

public class SliderColorTag extends SliderTag {

    private DataColor color;
    private int i;

    public SliderColorTag(int x, int y, int width, int height, DataColor color, int i) {
        super(x, y, width, height, new NumberRangeInt(0, 255));
        this.i = i;
        this.color = color;
        setColoredValue();
    }


    @Override
    public boolean mouseDragged(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
        if(super.mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_)){
            updateColor();
            return true;
        }
        return false;
    }

    public void setColoredValue() {
        switch(i) {
            case 1:
                setValue(color.getGreen());
                break;
            case 2:
                setValue(color.getBlue());
                break;
            default:
                setValue(color.getRed());
                break;
        }
    }

    public void updateColor() {
        switch(i) {
            case 1:
                color.setGreen(getValue());
                break;
            case 2:
                color.setBlue(getValue());
                break;
            default:
                color.setRed(getValue());
                break;
        }
    }
}
