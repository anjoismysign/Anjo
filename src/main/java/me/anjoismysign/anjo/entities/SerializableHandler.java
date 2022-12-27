package me.anjoismysign.anjo.entities;

import java.io.*;

public record SerializableHandler<T extends Serializable>(T value) {

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
