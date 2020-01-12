package creativeeditor.widgets;

import creativeeditor.data.DataRange;

public class SliderTag extends StyledSlider {
    public SliderTag(int x, int y, int width, int height, DataRange range)
    {
    	super(x, y, width, height, "", true, range.getNumber().intValue(), range.getMin(), range.getMax(), range);
    }
}
