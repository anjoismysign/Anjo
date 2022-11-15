package me.anjoismysign.anjo.entities;

import com.mongodb.lang.Nullable;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class UpdatableSerializable implements Serializable {
    private int version;

    private Serializable value;

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

    @Nullable
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
     * Crea un objeto que permite actualizarse.
     * La actualización se hace por parte de value,
     * por ejemplo dentro de su constructor.
     * Esto por ejemplo soluciona el típico
     * problema en el que en una base de datos
     * intentan serializar el objeto "Estudiante"
     * cuyos atributos son String cédula,
     * String nombreCompleto, int cuatrimestre.
     * Típicamente intentan serializar todos estos
     * atributos en cada campo de la base de datos,
     * Ahora por ejemplo sólo tomaría un campo en
     * lugar de 3. Si en algún momento por ejemplo
     * quieren agregar el atributo boolean fueVacunado
     * pueden simplemente agregar el atributo y
     * revisar si la version ya tenia ese boolean.
     * De no ser asi se lee como la vieja version y
     * se pasan los atributos al nuevo objeto con dicho
     * atributo.
     *
     * @param version Versión del objeto serializable.
     * @param value   El objeto serializable.
     */
    public UpdatableSerializable(int version, Serializable value) {
        this.version = version;
        this.version = version;
    }

    /**
     * @return La versión del objeto serializable.
     */
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return El objeto serializable.
     */
    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
}
