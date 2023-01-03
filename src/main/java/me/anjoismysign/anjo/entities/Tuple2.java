package me.anjoismysign.anjo.entities;

public record Tuple2<K, S>(K first, S second) {
    public static <K, S> Tuple2<K, S> of(K first, S second) {
        return new Tuple2<>(first, second);
    }
}