package infinityitemeditor.screen.nbt;

import net.minecraft.util.math.MathHelper;

public class Constraints {
    public static final Constraints NONE = Constraints.loose(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final int minWidth, maxWidth, minHeight, maxHeight;

    public Constraints(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public static Constraints withWidth(int minWidth, int maxWidth) {
        return new Constraints(minWidth, maxWidth, 0, Integer.MAX_VALUE);
    }

    public static Constraints withHeight(int minHeight, int maxHeight) {
        return new Constraints(0, Integer.MAX_VALUE, minHeight, maxHeight);
    }

    public static Constraints expand(int width, int height) {
        return Constraints.tight(width, height);
    }

    public static Constraints expandHeight(int width) {
        return Constraints.tight(width, Integer.MAX_VALUE);
    }

    public static Constraints expandWidth(int height) {
        return Constraints.tight(Integer.MAX_VALUE, height);
    }

    public static Constraints loose(int width, int height) {
        return new Constraints(0, width, 0, height);
    }

    public static Constraints tight(Size size) {
        return Constraints.tight(size.width(), size.height());
    }

    public static Constraints tight(int width, int height) {
        return new Constraints(width, width, height, height);
    }

    public static Constraints tightWidth(int width) {
        return Constraints.withWidth(width, width);
    }

    public static Constraints tightWidth(int width, int minHeight, int maxHeight) {
        return new Constraints(width, width, minHeight, maxHeight);
    }

    public static Constraints tightHeight(int height) {
        return Constraints.withHeight(height, height);
    }

    public static Constraints tightHeight(int minWidth, int maxWidth, int height) {
        return new Constraints(minWidth, maxWidth, height, height);
    }

    public static Constraints tightForFinite(int width, int height) {
        if (width != Integer.MAX_VALUE) {
            return height != Integer.MAX_VALUE ? Constraints.tight(width, height) : Constraints.tightWidth(width);
        }
        return height != Integer.MAX_VALUE ? Constraints.tightHeight(height) : Constraints.NONE;
    }

    public Size biggest() {
        return new Size(maxWidth, maxHeight);
    }

    public Size smallest() {
        return new Size(minWidth, minHeight);
    }

    public Constraints flipped() {
        return new Constraints(minHeight, maxHeight, minWidth, maxWidth);
    }

    public boolean hasBoundedWidth() {
        return maxWidth < Integer.MAX_VALUE;
    }

    public boolean hasBoundedHeight() {
        return maxHeight < Integer.MAX_VALUE;
    }

    public boolean hasInfiniteWidth() {
        return minWidth >= Integer.MAX_VALUE;
    }

    public boolean hasInfiniteHeight() {
        return minHeight >= Integer.MAX_VALUE;
    }

    public boolean isNormalized() {
        return minWidth >= 0 && minWidth <= maxWidth && minHeight >= 0 && minHeight <= maxHeight;
    }

    public int minWidth() {
        return minWidth;
    }

    public int maxWidth() {
        return maxWidth;
    }

    public int minHeight() {
        return minHeight;
    }

    public int maxHeight() {
        return maxHeight;
    }

    public boolean isTight() {
        return minWidth == maxWidth && minHeight == maxHeight;
    }

    public Size constrain(Size size) {
        return constrain(size.width(), size.height());
    }

    public Size constrain(int width, int height) {
        return new Size(constrainWidth(width), constrainHeight(height));
    }

    public int constrainWidth(int width) {
        return MathHelper.clamp(width, minWidth, maxWidth);
    }

    public int constrainHeight(int height) {
        return MathHelper.clamp(height, minHeight, maxHeight);
    }

    public Size constrainSizeAndAttemptToPreserveAspectRatio(Size size) {
        if (isTight())
            return smallest();

        double width = size.width();
        double height = size.height();

        assert size.width() > 0 && size.height() > 0;
        double aspectRatio = width / height;

        if (width > maxWidth) {
            width = maxWidth;
            height = width / aspectRatio;
        }

        if (height > maxHeight) {
            height = maxHeight;
            width = height * aspectRatio;
        }

        if (width < minWidth) {
            width = minWidth;
            height = width / aspectRatio;
        }

        if (height < minHeight) {
            height = minHeight;
            width = height * aspectRatio;
        }

        return constrain((int) width, (int) height);
    }

    public Constraints deflate(EdgeInsets edges) {
        assert edges != null;
        final int horizontal = edges.horizontal();
        final int vertical = edges.vertical();
        final int deflatedMinWidth = Math.max(0, minWidth - horizontal);
        final int deflatedMinHeight = Math.max(0, minHeight - vertical);
        return new Constraints(deflatedMinWidth, Math.max(deflatedMinWidth, maxWidth - horizontal), deflatedMinHeight, Math.max(deflatedMinHeight, maxHeight - vertical));
    }

    public Constraints enforce(Constraints constraints) {
        int minWidth = MathHelper.clamp(this.minWidth, constraints.minWidth, constraints.maxWidth);
        int maxWidth = MathHelper.clamp(this.maxWidth, constraints.minWidth, constraints.maxWidth);
        int minHeight = MathHelper.clamp(this.minHeight, constraints.minHeight, constraints.maxHeight);
        int maxHeight = MathHelper.clamp(this.maxHeight, constraints.minHeight, constraints.maxHeight);
        return new Constraints(minWidth, maxWidth, minHeight, maxHeight);
    }

    public Constraints widthConstraints() {
        return Constraints.withWidth(minWidth, maxWidth);
    }

    public Constraints heightConstraints() {
        return Constraints.withHeight(minHeight, maxHeight);
    }

    public boolean isSatisfiedBy(Size size) {
        return minWidth <= size.width() && size.width() <= maxWidth && minHeight <= size.height() && size.height() <= maxHeight;
    }

    public Constraints loosen() {
        return Constraints.loose(maxWidth, maxHeight);
    }

    public Constraints normalize() {
        if (isNormalized())
            return this;
        int minWidth = Math.max(this.minWidth, 0);
        int minHeight = Math.max(this.minHeight, 0);
        return new Constraints(minWidth, Math.max(minWidth, maxWidth), minHeight, Math.max(minHeight, maxHeight));
    }

    public Constraints tighten(int width, int height) {
        return Constraints.tight(MathHelper.clamp(width, minWidth, maxHeight), MathHelper.clamp(height, minHeight, maxHeight));
    }

    public Constraints tightenWidth(int width) {
        return Constraints.tightWidth(width, minHeight, maxHeight);
    }

    public Constraints tightenHeight(int height) {
        return Constraints.tightHeight(minWidth, maxWidth, height);
    }
}
