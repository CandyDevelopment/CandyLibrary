package fit.d6.candy.api.database;

import org.jetbrains.annotations.Nullable;

public interface DataProperty<T> {

    /**
     * Get the stored data
     *
     * @return data
     */
    @Nullable
    T get();

    /**
     * Set the value, if immutable will throw exception
     *
     * @param t value
     */
    void set(@Nullable T t);

    /**
     * @return If this property is mutable
     */
    boolean isMutable();

}
