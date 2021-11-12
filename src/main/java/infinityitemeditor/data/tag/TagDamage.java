package infinityitemeditor.data.tag;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.NumberRangeInt;

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
