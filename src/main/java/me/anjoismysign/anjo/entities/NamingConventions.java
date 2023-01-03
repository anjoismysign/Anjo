package me.anjoismysign.anjo.entities;

public class NamingConventions {
    public static String toCamelCase(String input) {
        StringBuilder sb = new StringBuilder();
        boolean nextToUpper = false;
        boolean firstLetter = true;
        for (char c : input.toCharArray()) {
            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                if (nextToUpper) {
                    sb.append(Character.toUpperCase(c));
                    nextToUpper = false;
                } else {
                    if (firstLetter) {
                        sb.append(Character.toLowerCase(c));
                        firstLetter = false;
                    } else {
                        sb.append(c);
                    }
                }
            } else if (c == ' ') {
                nextToUpper = true;
            }
        }
        return sb.toString();
    }

    public static String toPascalCase(String input) {
        StringBuilder sb = new StringBuilder();
        boolean nextToUpper = true;
        for (char c : input.toCharArray()) {
            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                if (nextToUpper) {
                    sb.append(Character.toUpperCase(c));
                    nextToUpper = false;
                } else {
                    sb.append(c);
                }
            } else if (c == ' ') {
                nextToUpper = true;
            }
        }
        return sb.toString();
    }

    public static String toSnakeCase(String input) {
        StringBuilder sb = new StringBuilder();
        boolean nextToUnderscore = true;
        for (char c : input.toCharArray()) {
            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                if (nextToUnderscore) {
                    sb.append(Character.toLowerCase(c));
                    nextToUnderscore = false;
                } else {
                    sb.append(c);
                }
            } else if (c == ' ') {
                sb.append('_');
                nextToUnderscore = true;
            }
        }
        return sb.toString();
    }

    public static String toScreamingSnakeCase(String input) {
        return toSnakeCase(input).toUpperCase();
    }
}
