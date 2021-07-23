package creativeeditor.util;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

public class RandomUtils {
    public static final Random RANDOM = new Random();


    public static int getRandomInRange(int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("min must be less than max");

        int sum = min + max;

        if (sum < 0)
            throw new IllegalArgumentException("min and max must have a positive sum");

        return RANDOM.nextInt(sum) - min;
    }

    @Nullable
    public static <T> T getRandomElement(List<T> list) {
        return list == null || list.isEmpty() ? null : list.get(RANDOM.nextInt(list.size()));
    }
}
