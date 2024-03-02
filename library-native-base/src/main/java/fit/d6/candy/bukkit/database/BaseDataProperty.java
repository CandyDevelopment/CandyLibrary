package fit.d6.candy.bukkit.database;

import fit.d6.candy.api.database.DataProperty;
import org.jetbrains.annotations.Nullable;

public class BaseDataProperty<T> implements DataProperty<T> {

    private T value;

    @Override
    public @Nullable T get() {
        return this.value;
    }

    @Override
    public void set(@Nullable T t) {
        this.value = t;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public String toString() {
        return this.value == null ? "" : this.value.toString();
    }

    public static <T> DataProperty<T> withDefault(T t) {
        DataProperty<T> dataProperty = new BaseDataProperty<>();
        dataProperty.set(t);
        return dataProperty;
    }

}
