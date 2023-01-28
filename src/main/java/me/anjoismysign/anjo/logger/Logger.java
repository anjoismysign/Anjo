package me.anjoismysign.anjo.logger;

public interface Logger {

    void log(String message);

    default void debug(String message) {
        log("DEBUG: " + message);
    }

    default void error(String message) {
        singleError(message);
        singleError(message);
        singleError(message);
    }

    default void singleError(String message) {
        log("ERROR: " + message);
    }
}
