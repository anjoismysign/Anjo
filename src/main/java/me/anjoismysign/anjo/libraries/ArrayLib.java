package me.anjoismysign.anjo.libraries;

public class ArrayLib {
    public static byte[] toPrimitive(Byte[] array) {
        byte[] byteArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) byteArray[i] = array[i];
        return byteArray;
    }
}
