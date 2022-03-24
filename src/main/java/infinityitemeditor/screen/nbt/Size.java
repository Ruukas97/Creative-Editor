package infinityitemeditor.screen.nbt;

public class Size {
    public static final Size ZERO = Size.square(0);

    private final int width, height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size fromHeight(int height){
        return new Size(Integer.MAX_VALUE, height);
    }

    public static Size fromWidth(int width){
        return new Size(width, Integer.MAX_VALUE);
    }

    public static Size fromRadius(int radius){
        return square(radius*2);
    }

    public static Size square(int dimensions){
        return new Size(dimensions, dimensions);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int longestSide() {
        return Math.max(width, height);
    }

    public int shortestSide() {
        return Math.min(width, height);
    }

    public Size expanded(int width, int height){
        return new Size(this.width + width, this.height + height);
    }

    public Size expanded(Size size){
        return expanded(size.width, size.height);
    }


    public Size expanded(EdgeInsets inset){
        return expanded(inset.horizontal(), inset.vertical());
    }

    public Size reduced(EdgeInsets insets){
        return new Size(width - insets.horizontal(), height - insets.vertical());
    }

    public double aspectRatio(){
        if (height != 0.0)
            return (double) width /(double) height;
        if (width > 0.0)
            return Double.POSITIVE_INFINITY;
        if (width < 0.0)
            return Double.NEGATIVE_INFINITY;
        return 0.0;
    }

    public boolean equals(Size size){
        return width == size.width && height == size.height;
    }

    public Size swapped(){
        return new Size(height, width);
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
