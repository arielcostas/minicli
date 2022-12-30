package dev.costas.minicli.annotation;

import java.lang.annotation.*;

/**
 * Annotation used to mark a class as a invokeable command.
 *
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	/**
	 * The name of the command, used to invoke it.
	 *
	 * @return The name of the command.
	 */
	String name();

	/**
	 * The short name of the command, used to invoke it.
	 *
	 * @return The short name of the command.
	 */
	String shortname() default "";

	/**
	 * The description of the command, used for the help command.
	 *
	 * @return The description of the command.
	 */
	String description() default "";
}
