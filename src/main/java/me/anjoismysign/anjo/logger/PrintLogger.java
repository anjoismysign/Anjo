package me.anjoismysign.anjo.logger;

/**
 * @author anjoismysign
 */
public class PrintLogger implements Logger {
    /**
     * Prints a message to the console.
     *
     * @param message the message to print
     */
    public void log(String message) {
        System.out.println(message);
    }
}
