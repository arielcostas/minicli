package dev.costas.minicli;

import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.defaults.ArgumentParser;
import dev.costas.minicli.exceptions.HelpException;
import dev.costas.minicli.exceptions.UnsupportedParameterTypeException;
import dev.costas.minicli.models.Invocation;

import java.lang.reflect.Field;

final class Inflater {
	/**
	 * Receives a class instance and injects the parameters and flags from the given arguments.
	 *
	 * @param instance The instance to inject the parameters and flags.
	 * @param args     The arguments to parse.
	 */
	static void inflateInstance(RunnableCommand instance, String[] args) throws IllegalAccessException, NumberFormatException, HelpException, UnsupportedParameterTypeException {
		ArgumentParser argumentParser = new ArgumentParser();
		var invocation = argumentParser.parse(args);
		var clazz = instance.getClass();

		if (invocation.getFlags().containsKey("help") || invocation.getFlags().containsKey("h")) {
			throw new HelpException(clazz);
		}

		for (Field field : clazz.getDeclaredFields()) {
			var flagAnnotation = field.getAnnotation(Flag.class);
			if (flagAnnotation != null) {
				if (flagAnnotation.name().equals("")) {
					throw new RuntimeException("Flag name cannot be empty.");
				}

				if (flagAnnotation.name().equals("help")) {
					throw new RuntimeException("Flag name cannot be 'help'.");
				}

				inflateFlag(field, instance, invocation);
				continue; // Skip the parameter injection since flags cannot be parameters.
			}

			var parameterAnnotation = field.getAnnotation(Parameter.class);
			if (parameterAnnotation != null) {
				if (parameterAnnotation.name().equals("")) {
					throw new RuntimeException("Parameter name cannot be empty.");
				}

				if (parameterAnnotation.name().equals("help") || parameterAnnotation.name().equals("h") || parameterAnnotation.shortName().equals("h")) {
					throw new RuntimeException("Parameter name cannot be 'help'.");
				}

				inflateParameter(field, instance, invocation);
			}
		}
	}

	static void inflateFlag(Field field, Object instance, Invocation invocation) throws IllegalAccessException {
		// Gets the annotation
		var flag = field.getAnnotation(Flag.class);

		// Must be a boolean, otherwise it's not a flag
		if (field.getType() != boolean.class && field.getType() != Boolean.class) {
			throw new RuntimeException("Flag " + field.getName() + " must be a boolean.");
		}

		// Gets the value of the flag with the long name
		var value = invocation.getFlag(flag.name());

		// If the value is null, gets the value of the flag with the short name (if it exists)
		if (value == null) {
			value = invocation.getFlag(flag.shortName());
		}

		// If the value is null, it means that the flag was not passed, use the default value
		if (value == null) {
			value = flag.defaultValue();
		}

		field.setAccessible(true);
		field.set(instance, value);
		field.setAccessible(false);
	}

	static void inflateParameter(Field field, RunnableCommand instance, Invocation invocation) throws IllegalAccessException, NumberFormatException, UnsupportedParameterTypeException {
		// Gets the annotation
		var parameterAnnotation = field.getAnnotation(Parameter.class);
		var value = invocation.getParameter(parameterAnnotation.name());

		// If the value is null, it means that the parameter was not passed, try with the short name
		if (value == null) {
			value = invocation.getParameter(parameterAnnotation.shortName());
		}

		// If the value is null, it means that the parameter was not passed, use the default value and throw if required
		if (value == null) {
			value = invocation.getParameter(parameterAnnotation.defaultValue());
		}

		// Try to parse the value to the correct type and set it to the field
		field.setAccessible(true);
		switch (field.getType().getName()) {
			case "java.lang.String" -> field.set(instance, value);
			case "int", "java.lang.Integer" -> field.set(instance, Integer.parseInt(value));
			case "long", "java.lang.Long" -> field.set(instance, Long.parseLong(value));
			case "float", "java.lang.Float" -> field.set(instance, Float.parseFloat(value));
			case "double", "java.lang.Double" -> field.set(instance, Double.parseDouble(value));
			default -> throw new UnsupportedParameterTypeException(
				instance.getClass().getName(), field.getName()
			);
		}
		field.setAccessible(false);
	}
}
