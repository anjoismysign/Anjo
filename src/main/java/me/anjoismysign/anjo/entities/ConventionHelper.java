package me.anjoismysign.anjo.entities;

/**
 * A class that helps with naming conventions.
 * An example of usage:
 * <pre>
 *     String camelCase = ConventionHelper.from("hello world").toCamelCase();
 *     String pascalCase = ConventionHelper.from("hello world").toPascalCase();
 *     String snakeCase = ConventionHelper.from("hello world").toSnakeCase();
 *     String screamingSnakeCase = ConventionHelper.from("hello world").toScreamingSnakeCase();
 *     String kebabCase = ConventionHelper.from("hello world").toKebabCase();
 *     String screamingKebabCase = ConventionHelper.from("hello world").toScreamingKebabCase();
 *
 *     assert camelCase.equals("helloWorld");
 *     assert pascalCase.equals("HelloWorld");
 *     assert snakeCase.equals("hello_world");
 *     assert screamingSnakeCase.equals("HELLO_WORLD");
 *     assert kebabCase.equals("hello-world");
 *     assert screamingKebabCase.equals("HELLO-WORLD");
 *     </pre>
 */
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
