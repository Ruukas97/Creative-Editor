package infinityitemeditor.data;

import infinityitemeditor.data.base.DataInteger;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.MathHelper;

public class NumberRangeInt extends DataInteger {
    protected @Getter
    @Setter
    int min, max;


    public NumberRangeInt(int number, int min, int max) {
        super(number);
        this.min = min;
        this.max = max;
    }


    public NumberRangeInt(int min, int max) {
        this(min, min, max);
    }


    @Override
    public void set(Integer number) {
        super.set(MathHelper.clamp(number, getMin(), getMax()));
    }
}
