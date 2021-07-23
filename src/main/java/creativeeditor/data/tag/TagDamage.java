package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;

public class TagDamage extends NumberRangeInt {
    private final DataItem item;


    public TagDamage(DataItem item) {
        super(0, 0);
        this.item = item;
    }


    public TagDamage(DataItem item, int value) {
        super(value, 0, 0);
        this.item = item;
    }

    @Override
    public int getMax() {
        return item.getItemStack().getMaxDamage();
    }
}
