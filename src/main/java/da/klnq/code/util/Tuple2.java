package da.klnq.code.util;

import java.util.function.Function;

public class Tuple2<T,U> {
    private final T value1;
    private final U value2;
    
    public Tuple2(T value1, U value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T getValue1() {
        return this.value1;
    }

    public U getValue2() {
        return this.value2;
    }

    public Tuple2<T,U> modify(Tuple2<Function<T,T>, Function<U,U>> modifiers) {
        assert modifiers != null;        
        return modify(modifiers.getValue1(), modifiers.getValue2());
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
