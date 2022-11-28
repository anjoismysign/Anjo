package me.anjoismysign.anjo.entities.uber;

public class Uber<T> {
    private T value;

    public static <T> Uber<T> drive(T value) {
        return new Uber<>(value);
    }

    public Uber(T value) {
        this.value = value;
    }

    public T thanks() {
        return value;
    }

    public void talk(T value) {
        this.value = value;
    }
}
