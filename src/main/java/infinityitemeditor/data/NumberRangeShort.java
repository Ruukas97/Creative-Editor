package infinityitemeditor.data;

import infinityitemeditor.data.base.DataShort;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.MathHelper;

public class NumberRangeShort extends DataShort {
    @Getter
    @Setter
    protected short min, max;


    public NumberRangeShort(short number, short min, short max) {
        super(number);
        this.min = min;
        this.max = max;
    }


    public NumberRangeShort(short min, short max) {
        this(min, min, max);
    }

    @Override
    public void set(Short value) {
        super.set((short) MathHelper.clamp(data, getMin(), getMax()));
    }
}
