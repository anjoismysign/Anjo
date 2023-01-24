package me.anjoismysign.anjo.entities;

import java.util.Optional;

public record Result<T>(T value, boolean isValid) {
    public static <T> Result<T> valid(T value) {
        return new Result<>(value, true);
    }

    public static <T> Result<T> invalid(T value) {
        return new Result<>(value, false);
    }

    public static <T> Result<T> ofNullable(T value) {
        return new Result<>(value, value != null);
    }

    public static <T> Result<T> invalidBecauseNull() {
        return new Result<>(null, false);
    }

    public Optional<T> toOptional() {
        if (!isValid) return Optional.empty();
        return Optional.of(value);
    }
}
