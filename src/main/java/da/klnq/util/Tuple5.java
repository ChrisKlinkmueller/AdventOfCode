package da.klnq.util;

public class Tuple5<T,U,V,W,X> {
    private final T value1;
    private final U value2;
    private final V value3;
    private final W value4;
    private final X value5;
    
    public Tuple5(T value1, U value2, V value3, W value4, X value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }

    public T get1() {
        return this.value1;
    }

    public U get2() {
        return this.value2;
    }

    public V get3() {
        return this.value3;
    }

    public W get4() {
        return this.value4;
    }

    public X get5() {
        return this.value5;
    }
    
}
