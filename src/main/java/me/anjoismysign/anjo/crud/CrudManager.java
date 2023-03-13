package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.entities.UpdatableSerializableHandler;
import me.anjoismysign.anjo.logger.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

public interface CrudManager<T extends Serializable> {
    /**
     * Creates a new instance of the Crudable and registers it in the database
     * using an identification of a random UUID.
     *
     * @return The new instance of the Crudable.
     */
    default T create() {
        return create(UUID.randomUUID().toString());
    }

    boolean exists(String id);

    /**
     * Creates a new instance of the Crudable and registers it in the database
     * using the given identification. Will only update the identification.
     *
     * @param id The identification to register the Crudable with.
     * @return The new instance of the Crudable.
     */
    T create(String id);


    /**
     * Will attempt to read the Crudable with the given id from the database.
     * If not found, will create a new instance of the Crudable and register it
     * using the given id.
     *
     * @param id The id of the Crudable to get
     * @return The Crudable with the given id
     */
    @NotNull
    T read(String id);

    /**
     * Will attempt to read the Crudable with the given id from the database.
     * If not found/exists, will return null.
     *
     * @param id The id of the Crudable to get
     * @return The Crudable with the given id
     */
    @Nullable
    T readOrNull(String id);

    /**
     * Updates a specificed Crudable in the database.
     *
     * @param crudable The Crudable to be updated
     */
    void update(T crudable);

    /**
     * Deletes the Crudable with the given id from the database.
     *
     * @param id The id of the Crudable to delete
     */
    void delete(String id);

    /**
     * Returns the logger for the CrudManager.
     * Some implementations may return null.
     *
     * @return The logger for the CrudManager
     */
    Logger getLogger();

    /**
     * Will instantiate a new UpdatableSerializableHandler with the given value and version.
     *
     * @param value   The value to be stored in the UpdatableSerializableHandler
     * @param version The version to be stored in the UpdatableSerializableHandler
     * @return The new UpdatableSerializableHandler
     */
    default UpdatableSerializableHandler<T> newUpdatable(T value, int version) {
        return new UpdatableSerializableHandler<>(value, version);
    }
}
