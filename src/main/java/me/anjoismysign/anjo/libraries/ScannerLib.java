package me.anjoismysign.anjo.libraries;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author anjoismysign
 */
public class ScannerLib {

    /**
     * Fancy way to query for an integer
     * if possible the value is returned, otherwise,
     * queries again for the value.
     *
     * @param toPrint list of messages you want
     * to print on System.out.println()
     * before scanning the integer
     * @param errorMessage error message that's being displayed.
     *                     '%input%' will be replaced with the input.
     * @return queried integer
     */
    public static int scanInt(List<String> toPrint, String errorMessage) {
        toPrint.forEach(System.out::println);
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println(errorMessage.replace("%input%", scanner.next()));
            return scanInt(toPrint, errorMessage);
        }
    }

    /**
     * Fancy way to query for a double
     * if possible the value is returned, otherwise,
     * queries again for the value.
     *
     * @param toPrint list of messages you want
     * to print on System.out.println()
     * before scanning the integer
     * @param errorMessage error message that's being displayed.
     *                     '%input%' will be replaced with the input.
     * @return queried double
     */
    public static double scanDouble(List<String> toPrint, String errorMessage) {
        toPrint.forEach(System.out::println);
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        } else {
            System.out.println(errorMessage.replace("%input%", scanner.next()));
            return scanDouble(toPrint, errorMessage);
        }
    }

    /**
     * Fancy way to query for a string.
     *
     * @param toPrint list of messages you want
     *                to print on System.out.println()
     *                before scanning the String.
     * @return the String.
     */
    public static String scanString(List<String> toPrint) {
        toPrint.forEach(System.out::println);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * fancy way to query for an integer
     * if possible the value is returned, otherwise,
     * queries again for the value
     *
     * @param toPrint message you want
     * to print on System.out.println()
     * before scanning the integer
     * @param errorMessage error message that's being displayed.
     *                     '%input%' will be replaced with the input.
     * @return queried integer
     */
    public static int scanInt(String toPrint, String errorMessage) {
        return scanInt(Collections.singletonList(toPrint), errorMessage);
    }

    /**
     * fancy way to query for a double
     * if possible the value is returned, otherwise,
     * queries again for the value
     *
     * @param toPrint message you want
     * to print on System.out.println()
     * before scanning the integer
     * @param errorMessage error message that's being displayed.
     *                     '%input%' will be replaced with the input.
     * @return queried double
     */
    public static double scanDouble(String toPrint, String errorMessage) {
        return scanDouble(Collections.singletonList(toPrint), errorMessage);
    }

    /**
     * Fancy way to query for a string.
     *
     * @param toPrint message you want
     *                to print on System.out.println()
     *                before scanning the String.
     * @return the String.
     */
    public static String scanString(String toPrint) {
        return scanString(Collections.singletonList(toPrint));
    }
}