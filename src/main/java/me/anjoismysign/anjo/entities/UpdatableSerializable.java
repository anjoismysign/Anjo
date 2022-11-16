package me.anjoismysign.anjo.entities;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class UpdatableSerializable implements Serializable {
    private int version;

    private Serializable value;

    /**
     * @param updatableSerializable the object to be serialized
     * @return byte array if succesful, null otherwise
     */
    public static byte[] serialize(UpdatableSerializable updatableSerializable) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(updatableSerializable);
            byte b[] = bos.toByteArray();
            out.close();
            bos.close();
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param updatableSerializable the object to be serialized
     * @return SerialBlob if successful, null if not
     */
    public static SerialBlob blobSerialize(UpdatableSerializable updatableSerializable) {
        byte[] bytes = serialize(updatableSerializable);
        SerialBlob blob = null;
        try {
            blob = new SerialBlob(bytes);
            return blob;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blob;
    }

    /**
     * @param bytes the byte array to be deserialized
     * @return UpdatableSerializable if successful, null otherwise
     */
    public static UpdatableSerializable deserialize(byte[] bytes) {
        UpdatableSerializable updatableSerializable;
        if (bytes == null)
            return null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            updatableSerializable = (UpdatableSerializable) in.readObject();
            return updatableSerializable;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param blob the blob to be deserialized
     * @return UpdatableSerializable if successful, null otherwise
     */
    public static UpdatableSerializable deserialize(Blob blob) {
        ByteArrayResult result = blobToByteArray(blob);
        if (!result.isValid())
            return null;
        return deserialize(result.value());
    }

    /**
     * @param blob blob that you want to get byte array
     * @return true if successful, false if an exception was catched.
     */
    public static ByteArrayResult blobToByteArray(Blob blob) {
        byte[] bytes = new byte[0];
        try {
            bytes = blob.getBinaryStream().readAllBytes();
            return new ByteArrayResult(bytes, true);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return new ByteArrayResult(bytes, false);
    }

    /**
     * Creates an object that can be updated.
     * The update is done by value,
     * for example within its constructor.
     * This for example solves the typical
     * problem in which in a database
     * they try to serialize the object "Student"
     * whose attributes are String id,
     * String fullName, int semester.
     * Typically they try to serialize all these
     * attributes in each field of the database,
     * Now for example it would only take one field
     * instead of 3. If at some point for example
     * they want to add the attribute boolean wasVaccinated
     * they can simply add the attribute and
     * check if the version already had that boolean.
     * If not, it is read as the old version and
     * the attributes are passed to the new object with that
     * attribute.
     *
     * @param version Version of the serializable object.
     * @param value   The serializable object.
     */
    public UpdatableSerializable(int version, Serializable value) {
        this.version = version;
        this.value = value;
    }

    /**
     * @return value's version
     */
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return The object that is serializable and updatable.
     */
    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
}
