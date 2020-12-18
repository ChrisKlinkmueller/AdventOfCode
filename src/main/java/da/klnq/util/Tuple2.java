package da.klnq.util;

import java.util.function.Function;

public class Tuple2<T,U> {
    private final T value1;
    private final U value2;
    
    public Tuple2(T value1, U value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T get1() {
        return this.value1;
    }

    public U get2() {
        return this.value2;
    }

    public Tuple2<T,U> modify(Tuple2<Function<T,T>, Function<U,U>> modifiers) {
        assert modifiers != null;        
        return modify(modifiers.get1(), modifiers.get2());
    }

    public Tuple2<T,U> modify(Function<T,T> modifier1, Function<U,U> modifier2) {
        assert modifier1 != null;
        assert modifier2 != null;
        
        return new Tuple2<>(
            modifier1.apply(this.value1), 
            modifier2.apply(this.value2)
        );
    }
}
