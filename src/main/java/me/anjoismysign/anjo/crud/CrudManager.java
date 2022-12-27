package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.entities.UpdatableSerializableHandler;
import me.anjoismysign.anjo.logger.Logger;

import java.io.Serializable;
import java.util.Optional;

public interface CrudManager<T extends Serializable> {
    T create();

    T read(String id);

    void update(T crudable);

    void delete(String id);

    Optional<Logger> getLogger();

    default UpdatableSerializableHandler<T> newUpdatable(T value, int version) {
        return new UpdatableSerializableHandler<>(value, version);
    }
}
