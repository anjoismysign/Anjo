package me.anjoismysign.anjo.entities;

import java.util.Optional;

public record Result<T>(T value, boolean isValid) {

    /**
     * @param value the value
     * @param <T>   the type of the value
     * @return a valid result
     */
    public static <T> Result<T> valid(T value) {
        return new Result<>(value, true);
    }

    /**
     * @param value the value
     * @param <T>   the type of the value
     * @return an invalid result
     */
    public static <T> Result<T> invalid(T value) {
        return new Result<>(value, false);
    }

    /**
     * @param value the value
     * @param <T>   the type of the value
     * @return If value is null will return an invalid result. Otherwise, a valid result.
     */
    public static <T> Result<T> ofNullable(T value) {
        return new Result<>(value, value != null);
    }

    /**
     * @param <T> the type of the value
     * @return an invalid result, since the value is null it's not required to pass it.
     */
    public static <T> Result<T> invalidBecauseNull() {
        return new Result<>(null, false);
    }

    /**
     * @return If not valid, will return an empty Optional. Otherwise, will return an Optional with the value.
     */
    public Optional<T> toOptional() {
        if (!isValid) return Optional.empty();
        return Optional.of(value);
    }
}
