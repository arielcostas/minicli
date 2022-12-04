package dev.costas.minicli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Flag {
	String name();

	String shortName();

	String description();

	boolean defaultValue() default false;

	boolean required() default false;
}
