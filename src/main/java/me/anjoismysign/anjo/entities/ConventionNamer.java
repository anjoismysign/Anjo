package me.anjoismysign.anjo.entities;

public class ConventionNamer {
    private final String value;

    public static ConventionNamer from(String value) {
        return new ConventionNamer(value);
    }

    private ConventionNamer(String value) {
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
