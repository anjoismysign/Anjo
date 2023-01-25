package me.anjoismysign.anjo.entities;

import java.io.*;

/**
 * A serializable handler
 *
 * @param value The value
 * @param <T>   The type
 */
public record SerializableHandler<T extends Serializable>(T value) {

    /**
     * Deserialize a byte array
     *
     * @param bytes The byte array
     * @return The SerializableHandler
     */
    public static SerializableHandler<Serializable> deserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in;
        try {
            in = new ObjectInputStream(bis);
            Serializable serializable = (Serializable) in.readObject();
            SerializableHandler<Serializable> handler = new SerializableHandler<>(serializable);
            in.close();
            bis.close();
            return handler;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Serializes the value to a byte array
     *
     * @return The byte array
     */
    public byte[] serialize() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(value);
            byte b[] = bos.toByteArray();
            out.close();
            bos.close();
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
