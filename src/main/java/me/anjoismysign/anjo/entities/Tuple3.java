package me.anjoismysign.anjo.entities;

public record Tuple3<K, S, U>(K first, S second, U third) {
    public static <K, S, U> Tuple3<K, S, U> of(K first, S second, U third) {
        return new Tuple3<>(first, second, third);
    }
}