package creativeeditor.data;

public interface Convertible<T, E extends Data<?, ?>> {
    public T convertFrom( E data );
}
