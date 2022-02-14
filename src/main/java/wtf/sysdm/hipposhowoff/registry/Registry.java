package wtf.sysdm.hipposhowoff.registry;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public interface Registry<T> {

    Map<String, T> getRegistry();

    default void register(@NotNull final String key, @NotNull final T value) {
        this.getRegistry().put(key, value);
    }

    @NotNull
    default Optional<T> get(@NotNull final String key) {
        return Optional.ofNullable(this.getRegistry().get(key));
    }

    default void iterate(@NotNull final BiConsumer<String, T> consumer) {

        for (final Map.Entry<String, T> entry : this.getRegistry().entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue());
        }

    }

    default boolean containsKey(@NotNull final String key) {
        return this.getRegistry().containsKey(key);
    }

    default boolean containsValue(@NotNull final T value) {
        return this.getRegistry().containsValue(value);
    }

    default Set<String> keySet() {
        return this.getRegistry().keySet();
    }

    default Set<Map.Entry<String, T>> entrySet() {
        return this.getRegistry().entrySet();
    }

    default Collection<T> values() {
        return this.getRegistry().values();
    }

}
