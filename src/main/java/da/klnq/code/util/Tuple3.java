package da.klnq.code.util;

public class Tuple3<T,U,V> {
    private final T value1;
    private final U value2;
    private final V value3;
    
    public Tuple3(T value1, U value2, V value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public T getValue1() {
        return this.value1;
    }

    public U getValue2() {
        return this.value2;
    }

    public V getValue3() {
        return value3;
    }
}
