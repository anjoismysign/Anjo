package me.anjoismysign.anjo.entities;

/**
 * @author anjoismysign
 */
public class Logger {
    /**
     * prints/logs a message
     *
     * @param message the message to print/log
     */
    public void log(String message) {
        System.out.println(message);
    }

    /**
     * prints/logs a message to debug
     *
     * @param message the message to print/log
     */
    public void debug(String message) {
        log("DEBUG: " + message);
        log("DEBUG: " + message);
        log("DEBUG: " + message);
    }

    /**
     * prints/logs a message to error
     *
     * @param message the message to print/log
     */
    public void error(String message) {
        log("ERROR: " + message);
        log("ERROR: " + message);
        log("ERROR: " + message);
    }

    /**
     * prints/logs a message to error (just one time)
     *
     * @param message the message to print/log
     */
    public void singleError(String message) {
        log("ERROR: " + message);
    }
}
