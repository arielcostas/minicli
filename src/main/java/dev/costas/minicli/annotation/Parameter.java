package dev.costas.minicli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a field of a {@link Command} class to be used as a parameter.
 * A parameter is a value that is passed to the command.
 * <p>
 * For example, you can declare a `@Parameter(name="username") String name` and the user will
 * use it with `--username VALUE`.
 *
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
	/**
	 * The name of the parameter. When the command is invoked, the parameter is passed as `--name value`.
	 *
	 * @return The name of the parameter.
	 */
	String name();

	/**
	 * The short name of the parameter. When the command is invoked, the parameter is passed as `-name value`.
	 * It's recommended to use a single character such as `n` or `f`.
	 *
	 * @return the short name of the parameter
	 */
	String shortname() default "";

	/**
	 * The description of the parameter. It's used in the help message.
	 *
	 * @return The description of the parameter.
	 */
	String description() default "";

	/**
	 * The default string value of the parameter. If the parameter is not passed, this value will be used.
	 * <p>
	 * Note that this value is a string, and will be converted to the type of the field.
	 * For example, if the field is an `int`, the value will be parsed as an integer.
	 * <p>
	 * If the default value is not a valid value for the field type, an exception will be thrown.
	 * <p>
	 * If the default value is an empty string, the field will be set to `null`.
	 *
	 * @return The default value of the parameter.
	 */
	String defaultValue() default "";
}
