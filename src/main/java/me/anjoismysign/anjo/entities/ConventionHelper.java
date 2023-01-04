package me.anjoismysign.anjo.entities;

public class ConventionHelper {
    private final String value;

    public static ConventionHelper from(String value) {
        return new ConventionHelper(value);
    }

    private ConventionHelper(String value) {
        this.value = value;
    }

    public String toCamelCase() {
        return NamingConventions.toCamelCase(value);
    }

    public String toPascalCase() {
        return NamingConventions.toPascalCase(value);
    }

    public String toSnakeCase() {
        return NamingConventions.toSnakeCase(value);
    }

    public String toScreamingSnakeCase() {
        return NamingConventions.toScreamingSnakeCase(value);
    }

    public String toKebabCase() {
        return NamingConventions.toKebabCase(value);
    }

    public String toScreamingKebabCase() {
        return NamingConventions.toScreamingKebabCase(value);
    }
}
