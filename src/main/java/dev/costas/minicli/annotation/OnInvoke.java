package dev.costas.minicli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a method as the one that will be invoked when
 * the command is executed.
 * <p>
 * A single public method returning {@code CommandOutput} must be annotated with this annotation.
 * If more than one method is annotated with this annotation, only one of them will be executed.
 *
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnInvoke {
}
