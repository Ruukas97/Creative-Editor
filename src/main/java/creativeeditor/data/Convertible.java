package creativeeditor.data;

public interface Convertible<T, E extends Data<?, ?>> {
    T convertFrom(E data);
}
