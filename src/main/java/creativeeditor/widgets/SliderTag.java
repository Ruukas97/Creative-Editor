package creativeeditor.widgets;

import creativeeditor.data.NumberRange;

public class SliderTag extends StyledSlider {
    public SliderTag(int x, int y, int width, int height, NumberRange range)
    {
    	super(x, y, width, height, "", true, range.getNumber().intValue(), range.getMin(), range.getMax(), range);
    }
}
