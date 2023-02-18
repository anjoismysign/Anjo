package me.anjoismysign.anjo.crud;

import java.sql.Connection;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SQLCrudManager<T extends Crudable> extends CrudManager<T> {
    String getCrudableKeyTypeName();

    String getTableName();

    String getPrimaryKeyName();

    default String crudableKeyTypePrepareStatement() {
        return getCrudableKeyTypeName() + "=?";
    }

    int getPrimaryKeyLength();

    Connection getConnection();

    boolean exists(String primary_key_id);

    void update(T crudable, int version);

    default T createAndRegister() {
        return createAndRegister(UUID.randomUUID().toString());
    }

    T createAndRegister(String id);

    default void forEachRecord(Consumer<T> consumer) {
        forEachRecord((crudable, version) -> consumer.accept(crudable));
    }

    void forEachRecord(BiConsumer<T, Integer> biConsumer);

    void reload();
}