public interface Operator<T> {
    T sum(T a, T b);
    T sub(T a, T b);
    T mult(T a, T b);
    T div(T a, T b);
    T zero();
}
