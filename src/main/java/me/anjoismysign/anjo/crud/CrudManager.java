package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.entities.UpdatableSerializableHandler;
import me.anjoismysign.anjo.logger.Logger;

import java.io.Serializable;
import java.util.UUID;

public interface CrudManager<T extends Serializable> {
    default T create() {
        return create(UUID.randomUUID().toString());
    }

    T create(String id);

    T read(String id);

    void update(T crudable);

    void delete(String id);

    Logger getLogger();

    default UpdatableSerializableHandler<T> newUpdatable(T value, int version) {
        return new UpdatableSerializableHandler<>(value, version);
    }
}
