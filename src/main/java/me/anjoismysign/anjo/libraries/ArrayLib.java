package me.anjoismysign.anjo.libraries;

public class ArrayLib {
    /**
     * Should convert a primitive byte array to a Byte (wrap for primitive byte) array
     *
     * @param array primitive byte array
     * @return Byte array
     */
    public static byte[] toPrimitive(Byte[] array) {
        byte[] byteArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) byteArray[i] = array[i];
        return byteArray;
    }
}
