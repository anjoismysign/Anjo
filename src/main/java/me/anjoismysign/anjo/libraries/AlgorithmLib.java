package me.anjoismysign.anjo.libraries;

/**
 * @author anjoismysign
 */
public class AlgorithmLib {

    /**
     * Runs code until the user wants to stop.
     * This method allows to give a title to a JOptionPane.
     *
     * @param runnable what you want to run while user prefers to continue
     * @param title    JOptionPane title
     * @param message  JOptionPane message/body
     */
    public static void dynamicRun(Runnable runnable, String title, String message) {
        boolean next = true;
        while (next) {
            runnable.run();
            next = PanelLib.requestBoolean(title, message);
        }
    }

    /**
     * Runs code until the user wants to stop.
     *
     * @param runnable what you wanna run while user prefers to continue
     * @param message  JOptionPane message/body
     */
    public static void dynamicRun(Runnable runnable, String message) {
        dynamicRun(runnable, "", message);
    }

    public static void ThreadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
