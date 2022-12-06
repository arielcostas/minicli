package dev.costas.minicli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark a field as a flag. A flag is a boolean value that can be set to true or false.
 * <p>
 * Flags can be set:
 * <ul>
 *     <li>Using the flag name, e.g. {@code --verbose}.</li>
 *     <li>Using the flag short name, e.g. {@code -v}.</li>
 *     <li>Using the flag name and value, e.g. {@code --verbose true}.</li>
 *     <li>Using the flag short name and value, e.g. {@code -v true}.</li>
 * </ul>
 *
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Flag {
	/**
	 * The name of the flag. This is the name that will be used to set the flag.
	 * <p>
	 * For example, if the name is {@code verbose}, the flag can be set using {@code --verbose}.
	 */
	String name();

	/**
	 * The short name of the flag. This is an optional abbreviation of the flag name, and should be a single character.
	 * <p>
	 * For example, if the short name is {@code v}, the flag can be set using {@code -v}.
	 */
	String shortName();

	/**
	 * A description of the flag. This is used to generate the help message.
	 */
	String description();

	/**
	 * The default value of the flag. This is the value that will be used if the flag is not set.
	 */
	boolean defaultValue() default false;
}
