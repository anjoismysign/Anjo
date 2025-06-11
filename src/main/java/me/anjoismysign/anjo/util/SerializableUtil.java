package me.anjoismysign.anjo.util;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableUtil {

    /**
     * @param serializable the object to be serialized
     * @return byte array if succesful, null otherwise
     */
    public static byte @Nullable [] serialize(Serializable serializable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput;
        try {
            objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(serializable);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            objectOutput.close();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @param bytes the byte array to be deserialized
     * @return Serializable if successful, null otherwise
     */
    @Nullable
    public static Serializable deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream)) {
            return (Serializable) objectInput.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
