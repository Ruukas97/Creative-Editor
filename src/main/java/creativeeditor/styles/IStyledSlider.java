package creativeeditor.styles;

public interface IStyledSlider<T extends Number> extends IStyledWidget {
    public T getValue();


    public T getMin();


    public T getMax();
}
