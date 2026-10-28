package fr.iglee42.igleelib.function;

import java.util.Objects;

/**
 * Represents an operation that accepts four input arguments and returns no result.
 */
@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {

    void accept(T t, U u, V v, W w);

    default QuadConsumer<T, U, V, W> andThen(final QuadConsumer<? super T, ? super U, ? super V, ? super W> after) {
        Objects.requireNonNull(after);
        return (t, u, v, w) -> {
            accept(t, u, v, w);
            after.accept(t, u, v, w);
        };
    }
}
