package infinityitemeditor.styles;

public interface IStyledSlider<T extends Number> extends IStyledWidget {
    T getValue();


    T getMin();


    T getMax();
}
