package me.anjoismysign.anjo.libraries;

import javax.swing.*;
import java.util.Locale;

/**
 * @author anjoismysign
 */
public class PanelLib {
    /**
     * Translates UI buttons to Spanish.
     * Should be run inside main method before running code that makes use of Java Swing, here an example:
     * <p>
     * public static void main(String[] args) {
     * PanelLib.esEs();
     * // code that makes use of Java Swing
     * }
     */
    public static void esEs() {
        UIManager.put("OptionPane.cancelButtonText", "Anular");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "Acepto");
        UIManager.put("OptionPane.yesButtonText", "Si");
    }

    /**
     * Requests a boolean in a fancier way.
     * In case of the window being closed, recursion will be used.
     * In case of the body being written in spanish,
     * I recommend using PanelLib#esEs() one time before
     * running this method.
     * This method allows you to give a title to the JOptionPane.
     *
     * @param title   JOptionPane title.
     * @param message JOptionPane message/body.
     * @return true if the user clicked "yes", false if the user clicked "no".
     */
    public static boolean requestBoolean(String title, String message) {
        JOptionPane.setRootFrame(null);
        int x = JOptionPane.showConfirmDialog(null, message, title,
                0, -1);
        if (x == JOptionPane.CLOSED_OPTION)
            return requestBoolean(title, message);
        return x == 0 ? true : false;
    }

    /**
     * Requests a boolean in a fancier way.
     * In case of the window being closed, recursion will be used.
     * In case of the body being written in spanish,
     * I recommend using PanelLib#esEs() one time before
     * running this method.
     *
     * @param message JOptionPane message/body.
     * @return true if the user clicked "yes", false if the user clicked "no".
     */
    public static boolean requestBoolean(String message) {
        return requestBoolean("", message);
    }

    /**
     * Requests a boolean through a JOptionPane.
     * In case of not being addressed either 'yes' or 'no',
     * recursion will be used.
     * This method allows you to give a title to the JOptionPane.
     *
     * @param title        JOptionPane title.
     * @param errorMessage Message shown in case of being invalid the input.
     * @param message      Message shown before 'yes' and 'no' buttons.
     */
    public static boolean confirm(String title, String message, String errorMessage) {
        String input = PanelLib.requestString(message + " \nType 'yes' or 'no'", errorMessage);
        String lowercased = input.toLowerCase(Locale.ROOT);
        if (!lowercased.equals("yes") && !lowercased.equals("no")) {
            PanelLib.showMessage(title, "'" + input + "' is neither 'yes' or 'no'");
            return confirm(title, message);
        }
        if (lowercased.equals("yes"))
            return true;
        else
            return false;
    }

    /**
     * Requests a boolean through a JOptionPane.
     * In case of not being addressed either 'yes' or 'no',
     * recursion will be used.
     *
     * @param message      Message shown before 'yes' and 'no' buttons.
     * @param errorMessage Message shown in case of being invalid the input.
     */
    public static boolean confirm(String message, String errorMessage) {
        return confirm("", message, errorMessage);
    }

    /**
     * Requests an integer through a JOptionPane.
     * Recursion will be used until provided an integer.
     * This method allows you to give a title to the JOptionPane.
     *
     * @param title        JOptionPane title.
     * @param message      Message shown througn the input field.
     * @param errorMessage Message shown when the user doesn't provide an integer.
     *                     '%input%' will be replaced by the user's input.
     * @return the integer
     */
    public static int requestInteger(String title, String message, String errorMessage) {
        String input = requestString(title, message);
        int x = 0;
        try {
            x = Integer.parseInt(input);
            return x;
        } catch (NumberFormatException e) {
            showMessage("ERROR", errorMessage.replace("%input%", input));
            return requestInteger(title, message, errorMessage);
        }
    }

    /**
     * Requests an integer through a JOptionPane.
     * Recursion will be used until provided an integer.
     *
     * @param message      Message shown througn the input field.
     * @param errorMessage Message shown when the user doesn't provide an integer.
     * @return the integer
     */
    public static int requestInteger(String message, String errorMessage) {
        return requestInteger("", message, errorMessage);
    }

    /**
     * Requests a double through a JOptionPane.
     * Recursion will be used until provided a double.
     * This method allows you to give a title to the JOptionPane.
     *
     * @param title        JOptionPane title.
     * @param message      Message shown througn the input field.
     * @param errorMessage Message shown when the user doesn't provide a double.
     *                     '%input%' will be replaced by the user's input.
     * @return the double
     */
    public static double requestDouble(String title, String message, String errorMessage) {
        String input = requestString(title, message);
        double x = 0;
        try {
            x = Double.parseDouble(input);
            return x;
        } catch (NumberFormatException e) {
            showMessage("ERROR", errorMessage.replace("%input%", input));
            return requestDouble(title, message, errorMessage);
        }
    }

    /**
     * Requests a double through a JOptionPane.
     * Recursion will be used until provided a double.
     *
     * @param message      Message shown througn the input field.
     * @param errorMessage Message shown when the user doesn't provide a double.
     *                     '%input%' will be replaced by the user's input.
     * @return the double
     */
    public static double requestDouble(String message, String errorMessage) {
        return requestDouble("", message, errorMessage);
    }

    /**
     * Requests a String through a JOptionPane.
     * Recursion will be used until provided a String.
     *
     * @param message      Message shown througn the input field.
     * @param errorMessage Message shown when the user doesn't provide a String.
     * @return the String
     */
    public static String requestString(String message, String errorMessage) {
        String input = requestString("", message);
        return input;
    }

    /**
     * Requests a String through a JOptionPane.
     * Recursion will be used until provided a String.
     * This method allows you to give a title to the JOptionPane.
     *
     * @param title        JOptionPane title.
     * @param message      Message shown througn the input field.
     * @param errorMessage Message shown when the user doesn't provide a String.
     * @return the String
     */
    public static String requestString(String title, String message, String errorMessage) {
        String input = JOptionPane.showInputDialog(null, message, title, -1);
        if (input == null || input.length() == 0) {
            showMessage("ERROR", errorMessage);
            return requestString(title, message, errorMessage);
        }
        return input;
    }

    /**
     * Displays a message through a JOptionPane.
     *
     * @param message Message to be shown.
     */
    public static void showMessage(String message) {
        showMessage("", message);
    }

    /**
     * Displays a message through a JOptionPane.
     * This method allows you to give a title to the JOptionPane.
     *
     * @param title   JOptionPane title.
     * @param message Message to be shown.
     */
    public static void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, -1);
    }
}