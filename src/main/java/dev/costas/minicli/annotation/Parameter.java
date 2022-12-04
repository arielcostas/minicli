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
	String shortName() default "";

	/**
	 * The description of the parameter. It's used in the help message.
	 *
	 * @return The description of the parameter.
	 */
	String description() default "";

	/**
	 * Whether the parameter is required or not. If it's required and it's not passed, the command will fail.
	 * If it's not required, the command will use the default value if it's not passed.
	 *
	 * @return true if the parameter is required, false otherwise
	 */
	boolean required() default false;

	/**
	 * The default value of the parameter. It's used if the parameter is not required and it's not passed.
	 *
	 * @return The default value of the parameter.
	 */
	String defaultValue() default "";
}
