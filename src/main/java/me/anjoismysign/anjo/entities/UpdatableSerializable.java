package me.anjoismysign.anjo.entities;

import me.anjoismysign.anjo.libraries.ArrayLib;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public interface UpdatableSerializable<T extends Serializable> extends Serializable {
    /*
      Creates an object that can be updated.
      The update is done by value,
      for example within its constructor.
      This for example solves the typical
      problem in which in a database
      they try to serialize the object "Student"
      whose attributes are String id,
      String fullName, int semester.
      Typically they try to serialize all these
      attributes in each field of the database,
      Now for example it would only take one field
      instead of 3. If at some point for example
      they want to add the attribute boolean wasVaccinated
      they can simply add the attribute and
      check if the version already had that boolean.
      If not, it is read as the old version and
      the attributes are passed to the new object with that
      attribute.
     */

    /**
     * @param updatableSerializable the object to be serialized
     * @return byte array if succesful, null otherwise
     */
    static byte[] serialize(UpdatableSerializable updatableSerializable) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(updatableSerializable);
            byte[] b = bos.toByteArray();
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
    static SerialBlob blobSerialize(UpdatableSerializable updatableSerializable) {
        byte[] bytes = serialize(updatableSerializable);
        SerialBlob blob;
        try {
            blob = new SerialBlob(bytes);
            return blob;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bytes the byte array to be deserialized
     * @return UpdatableSerializable if successful, null otherwise
     */
    static UpdatableSerializable deserialize(byte[] bytes) {
        UpdatableSerializable updatableSerializable;
        if (bytes == null)
            return null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in;
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
    static UpdatableSerializable deserialize(Blob blob) {
        Result<Byte[]> result = blobToByteArray(blob);
        if (!result.isValid())
            return null;
        return deserialize(ArrayLib.toPrimitive(result.value()));
    }

    /**
     * @param blob blob that you want to get byte array
     * @return true if successful, false if an exception was catched.
     */
    static Result<Byte[]> blobToByteArray(Blob blob) {
        byte[] bytes = new byte[0];
        try {
            bytes = blob.getBinaryStream().readAllBytes();
            return new Result(bytes, true);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return new Result(bytes, false);
    }

    /**
     * @return value's version
     */
    int getVersion();

    void setVersion(int version);

    /**
     * @return The updatable serializable object
     */
    T getValue();

    void setValue(T value);

    default byte[] serialize() {
        return serialize(this);
    }

    default SerialBlob blobSerialize() {
        return blobSerialize(this);
    }
}
