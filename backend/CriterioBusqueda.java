public interface CriterioBusqueda<T> {
    boolean coincide(T dato, String valor);
}