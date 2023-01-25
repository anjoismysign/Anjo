package me.anjoismysign.anjo.entities;

import java.io.Serializable;

/**
 * An updatable serializable handler
 *
 * @param value   The value/object which extends Serializable
 * @param version The version of the value
 * @param <T>     The type of the value/object
 */
public record UpdatableSerializableHandler<T extends Serializable>(T value,
                                                                   int version) implements UpdatableSerializable<T> {

    public static <T extends Serializable> UpdatableSerializableHandler<T> of(T value, int version) {
        return new <T>UpdatableSerializableHandler<T>(value, version);
    }

    /**
     * @return The version of the value/object
     */
    @Override
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version of the value/object
     *
     * @param version The version
     */
    @Override
    public void setVersion(int version) {
    }

    /**
     * @return The value/object
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * Sets the value/object
     *
     * @param value The value/object
     */
    @Override
    public void setValue(T value) {
    }
}
