package me.anjoismysign.anjo.entities;

import java.io.Serializable;

public record UpdatableSerializableHandler<T extends Serializable>(T value,
                                                                   int version) implements UpdatableSerializable<T> {

    public static <T extends Serializable> UpdatableSerializableHandler<T> of(T value, int version) {
        return new <T>UpdatableSerializableHandler<T>(value, version);
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
    }
}
