package me.anjoismysign.anjo.entities;

public record Tuple4<K, S, U, V>(K first, S second, U third, V fourth) {
    public static <K, S, U, V> Tuple4<K, S, U, V> of(K first, S second, U third, V fourth) {
        return new Tuple4<>(first, second, third, fourth);
    }
}