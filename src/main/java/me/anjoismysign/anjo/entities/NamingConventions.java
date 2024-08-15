package me.anjoismysign.anjo.entities;

import java.util.Locale;

public class NamingConventions {
    public static String toCamelCase(String input) {
        // Removes all non-alphanumeric characters from input
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        // Splits input into an array of words
        String[] words = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

        // Converts the first word to lower case and appends it to the result string
        StringBuilder result = new StringBuilder(words[0].toLowerCase(Locale.ROOT));

        // Applies camelCase to the rest.
        for (int i = 1; i < words.length; i++) {
            result.append(words[i].substring(0, 1).toUpperCase(Locale.ROOT))
                    .append(words[i].substring(1).toLowerCase(Locale.ROOT));
        }

        return result.toString();
    }

    public static String toPascalCase(String input) {
        // Removes all non-alphanumeric characters from input
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        // Splits input into an array of words
        String[] words = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

        // Applies to PascalCase the rest
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase(Locale.ROOT))
                    .append(word.substring(1).toLowerCase(Locale.ROOT));
        }

        return result.toString();
    }

    public static String toSnakeCase(String input) {
        // Removes all non-alphanumeric characters from input
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        // Splits input into an array of words
        String[] words = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

        // Appends each word to the result in lowercase, separated by underscores
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.toLowerCase(Locale.ROOT)).append("_");
        }

        // Removes the trailing underscore
        result.setLength(result.length() - 1);

        return result.toString();
    }

    public static String toScreamingSnakeCase(String input) {
        // Removes all non-alphanumeric characters from input
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        // Splits input into an array of words
        String[] words = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

        // Appends each word to the result in uppercase, separated by underscores
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.toUpperCase(Locale.ROOT)).append("_");
        }

        // Remove the trailing underscore
        result.setLength(result.length() - 1);

        return result.toString();
    }

    public static String toKebabCase(String input) {
        // Removes all non-alphanumeric characters from input
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        // Splits input into an array of words
        String[] words = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

        // Appends each word to the result in lowercase, separated by dashes
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.toLowerCase(Locale.ROOT)).append("-");
        }

        // Removes the trailing hyphen
        result.setLength(result.length() - 1);

        return result.toString();
    }

    public static String toScreamingKebabCase(String input) {
        // Removes all non-alphanumeric characters from input
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        // Splits input into an array of words
        String[] words = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

        // Appends each word to the result in uppercase, separated by dashes
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.toUpperCase(Locale.ROOT)).append("-");
        }

        // Removes the trailing hyphen
        result.setLength(result.length() - 1);

        return result.toString();
    }
}
