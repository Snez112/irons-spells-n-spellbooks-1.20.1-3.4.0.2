package net.minecraftforge.common.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Fabric-port shim of Forge's LazyOptional: a lazily-resolved, memoized Optional.
 */
public class LazyOptional<T> {

    private final Supplier<T> supplier;
    private volatile Object value = UNRESOLVED;
    private static final Object UNRESOLVED = new Object();

    private LazyOptional(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> LazyOptional<T> of(Supplier<T> supplier) {
        return new LazyOptional<>(supplier);
    }

    @SuppressWarnings("unchecked")
    public static <T> LazyOptional<T> empty() {
        return (LazyOptional<T>) EMPTY;
    }

    private static final LazyOptional<?> EMPTY = new LazyOptional<>(() -> null);

    @SuppressWarnings("unchecked")
    public <X> LazyOptional<X> cast() {
        return (LazyOptional<X>) this;
    }

    public boolean isPresent() {
        return supplier != null && resolve().isPresent();
    }

    public void invalidate() {
        value = UNRESOLVED;
    }

    public void ifPresent(Consumer<? super T> action) {
        resolve().ifPresent(action);
    }

    @SuppressWarnings("unchecked")
    public Optional<T> resolve() {
        if (supplier == null) {
            return Optional.empty();
        }
        if (value == UNRESOLVED) {
            synchronized (this) {
                if (value == UNRESOLVED) {
                    value = supplier.get();
                }
            }
        }
        return Optional.ofNullable((T) value);
    }

    public T orElse(T other) {
        return resolve().orElse(other);
    }

    public <X> LazyOptional<X> map(Function<? super T, ? extends X> mapper) {
        return isPresent() ? new LazyOptional<>(() -> resolve().map(mapper).orElse(null)) : LazyOptional.empty();
    }
}