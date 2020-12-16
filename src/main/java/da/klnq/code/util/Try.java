package da.klnq.code.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Try<T> {
    public static final Try<Void> SUCCESS = of(null);
	public static final Try<Void> NOT_IMPLEMENTED = failure("Method not implemented.");

	private final T value;
	private final Exception exception;

	private Try(T value, Exception ex) {
		this.value = value;
		this.exception = ex;
	}

	public boolean isFailure() {
		return this.exception != null;
	}

	public T get() {
		return this.value;
	}

	public Exception exception() {
		return this.exception;
	}

	public <U> Try<U> map(Function<T, Try<U>> mapper) {
		assert mapper != null;
		return replace(() -> mapper.apply(this.value));
	}

	public <U> Try<U> mapValue(Function<T, U> mapper) {
		assert mapper != null;
		return replace(() -> of(mapper.apply(this.value)));
	}

	public <U> Try<U> replace(Supplier<Try<U>> supplier) {
		assert supplier != null;

		if (this.isFailure()) {
			return failure(this.exception);
		}

		return supplier.get();
	}

	public <U> Try<U> replaceValue(Supplier<U> supplier) {
		assert supplier != null;
		return replace(() -> of(supplier.get()));
	}

	public Try<T> execute(Runnable runner) {
		assert runner != null;
		runner.run();
		return this;
	}

	public Try<T> consume(Consumer<T> consumer) {
		assert consumer != null;
		consumer.accept(this.value);
		return this;
	}

	public <U> Try<U> cast() {
		if (this.isFailure()) {
			return failure(this.exception);
		}

		try {
			@SuppressWarnings("unchecked")
			final Try<U> result = of((U) this.value);
			return result;
		} catch (ClassCastException ex) {
			return Try.failure("Error when casting result.", ex);
		}
	}

	public Try<T> handleException(Consumer<Exception> handler) {
		assert handler != null;
		if (this.isFailure()) {
			handler.accept(this.exception);
		}
		return this;
	}

	public static <T> Try<T> of(T value) {
		return new Try<>(value, null);
	}

	public static <T> Try<T> failure(String message) {
		assert message != null;
		return new Try<>(null, new Exception(message));
	}

	public static <T> Try<T> failure(
		String format,
		Object... args
	) {
		assert format != null;
		final String message = String.format(format, args);
		return failure(message);
	}

	public static <T> Try<T> failure(Exception ex) {
		assert ex != null;
		return new Try<>(null, ex);
	}

	public static <T> Try<T> failure(
		Exception ex,
		String message
	) {
		assert message != null;
		assert ex != null;
		return new Try<>(null, new Exception(message, ex));
	}

	public static <T> Try<T> failure(
		Exception ex,
		String format,
		Object... args
	) {
		assert format != null;
		assert ex != null;
		final String message = String.format(format, args);
		return failure(ex, message);
	}
}
