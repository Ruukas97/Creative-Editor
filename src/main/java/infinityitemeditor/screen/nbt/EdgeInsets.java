package infinityitemeditor.screen.nbt;

import net.minecraft.util.math.MathHelper;

public class EdgeInsets {
    public static final EdgeInsets ZERO = EdgeInsets.all(0);

    private final int left, top, right, bottom;

    public EdgeInsets(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public static EdgeInsets all(int value) {
        return new EdgeInsets(value, value, value, value);
    }

    public static EdgeInsets fromLTRB(int left, int top, int right, int bottom) {
        return new EdgeInsets(left, top, right, bottom);
    }

    public static EdgeInsets onlyLeft(int left) {
        return new EdgeInsets(left, 0, 0, 0);
    }

    public static EdgeInsets onlyTop(int top) {
        return new EdgeInsets(0, top, 0, 0);
    }

    public static EdgeInsets onlyRight(int right) {
        return new EdgeInsets(0, 0, right, 0);
    }

    public static EdgeInsets onlyBottom(int bottom) {
        return new EdgeInsets(0, 0, 0, bottom);
    }

    public static EdgeInsets symmetricHorizontal(int value) {
        return new EdgeInsets(value, 0, value, 0);
    }

    public static EdgeInsets symmetricVertical(int value) {
        return new EdgeInsets(0, value, 0, value);
    }

    public static EdgeInsets symmetric(int horizontal, int vertical) {
        return new EdgeInsets(horizontal, vertical, horizontal, vertical);
    }

    public static EdgeInsets symmetric(Axis axis, int value) {
        switch (axis) {
            case VERTICAL:
                return symmetricHorizontal(value);
            case HORIZONTAL:
                return symmetricVertical(value);
        }
        return ZERO;
    }

    public EdgeInsets clamp(EdgeInsets min, EdgeInsets max) {
        return new EdgeInsets(MathHelper.clamp(left, min.left, max.left), MathHelper.clamp(top, min.top, max.top), MathHelper.clamp(right, min.right, max.right), MathHelper.clamp(bottom, min.bottom, max.bottom));
    }

    public EdgeInsets add(EdgeInsets other) {
        return new EdgeInsets(left + other.left, top + other.top, right + other.right, bottom + other.bottom);
    }

    public boolean equals(EdgeInsets other) {
        return left == other.left && top == other.top && right == other.top && bottom == other.bottom;
    }

    public EdgeInsets inset(Size size) {
        return new EdgeInsets(left, top, left + size.width() - right, top + size.height() - bottom);
    }

    public Size area(Size size) {
        return size.reduced(this);
    }

    public boolean isZero() {
        return equals(ZERO);
    }

    public int left() {
        return left;
    }

    public int top() {
        return top;
    }

    public int right() {
        return right;
    }

    public int bottom() {
        return bottom;
    }

    public int horizontal() {
        return left + right;
    }

    public int vertical() {
        return top + bottom;
    }
}
