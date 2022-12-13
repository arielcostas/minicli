package dev.costas.minicli;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.exceptions.QuitException;
import dev.costas.minicli.framework.ArgumentParser;
import dev.costas.minicli.framework.CommandExecutor;
import dev.costas.minicli.framework.HelpGenerator;
import dev.costas.minicli.framework.Instantiator;
import dev.costas.minicli.models.ApplicationParams;
import dev.costas.minicli.models.Invocation;
import org.reflections.Reflections;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * The main class of the application.
 *
 * @since 1.0.0
 */
public class MinicliApplication {
	private final ArgumentParser argumentParser;
	private final CommandExecutor commandExecutor;
	private final HelpGenerator helpGenerator;
	private final OutputStream outputStream;
	private final ApplicationParams application;
	private final Instantiator instantiator;

	private static final List<String> forbiddenCommands = List.of("help", "quit", "h", "q", "exit");

	/**
	 * Creates a new instance of the application.
	 * @param argumentParser The argument parser to use.
	 * @param commandExecutor The command executor to use.
	 * @param helpGenerator The help generator to use.
	 * @param outputStream The output stream to use.
	 * @param application The application parameters.
	 * @param instantiator The instantiator to use.
	 */
	protected MinicliApplication(ArgumentParser argumentParser, CommandExecutor commandExecutor, HelpGenerator helpGenerator, OutputStream outputStream, ApplicationParams application, Instantiator instantiator) {
		this.argumentParser = argumentParser;
		this.commandExecutor = commandExecutor;
		this.helpGenerator = helpGenerator;
		this.outputStream = outputStream;
		this.application = application;
		this.instantiator = instantiator;
	}

	/**
	 * Gets an instance of the application builder.
	 * @return An instance of the application builder.
	 */
	public static MinicliApplicationBuilder builder() {
		return new MinicliApplicationBuilder();
	}

	/**
	 * Scans the given package for commands and executes the command with the given arguments.
	 *
	 * @param clazz The package to scan for commands. It also scans subpackages.
	 * @param args  The arguments to pass to the command.
	 * @throws QuitException If the user wants to quit the application.
	 * @throws IllegalAccessException If the command class cannot be instantiated.
	 */
	public void run(Class<?> clazz, String[] args) throws QuitException, IllegalAccessException {
		var classes = getCommands(clazz.getPackageName());
		if (args.length == 0) {
			this.helpGenerator.show(application, classes, outputStream);
			return;
		}

		if (args[0].equals("help")) {
			this.helpGenerator.show(application, classes, outputStream);
			return;
		}

		if (args[0].equals("quit") || args[0].equals("q") || args[0].equals("exit")) {
			throw new QuitException();
		}

		var candidades = classes.stream()
				.filter(c -> {
					var name = c.getAnnotation(Command.class).name().toLowerCase();
					var shortName = c.getAnnotation(Command.class).shortname().toLowerCase();
					return name.equals(args[0]) || shortName.equals(args[0]);
				})
				.toList();

		switch (candidades.size()) {
			case 0 -> throw new RuntimeException("Command not found.");
			case 1 -> {
				var instance = instantiator.getInstance(candidades.get(0));

				if (!(instance instanceof RunnableCommand)) {
					throw new RuntimeException("Command class must implement RunnableCommand");
				}

				inflateInstance(instance, args);
				var invocation = argumentParser.parse(args);
				commandExecutor.execute((RunnableCommand) instance, invocation, outputStream);
			}
			default -> throw new RuntimeException("Multiple commands with the same name found.");

		}

	}

	/**
	 * Receives a class instance and injects the parameters and flags from the given arguments.
	 *
	 * @param instance The instance to inject the parameters and flags.
	 * @param args     The arguments to parse.
	 */
	private void inflateInstance(Object instance, String[] args) throws IllegalAccessException, NumberFormatException {
		var invocation = argumentParser.parse(args);
		var clazz = instance.getClass();

		for (Field field : clazz.getDeclaredFields()) {
			var flagAnnotation = field.getAnnotation(Flag.class);
			if (flagAnnotation != null) {
				inflateFlag(field, instance, invocation);
				continue; // Skip the parameter injection since flags cannot be parameters.
			}

			var parameterAnnotation = field.getAnnotation(Parameter.class);
			if (parameterAnnotation != null) {
				inflateParameter(field, instance, invocation);
			}
		}
	}

	private void inflateFlag(Field field, Object instance, Invocation invocation) throws IllegalAccessException {
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

		field.set(instance, value);
	}

	private void inflateParameter(Field field, Object instance, Invocation invocation) throws IllegalAccessException, NumberFormatException {
		// Gets the annotation
		var parameterAnnotation = field.getAnnotation(Parameter.class);
		var value = invocation.getParameter(parameterAnnotation.name());

		// If the value is null, it means that the parameter was not passed, try with the short name
		if (value == null) {
			value = invocation.getParameter(parameterAnnotation.shortName());
		}

		// If the value is null, it means that the parameter was not passed, use the default value and throw if required
		if (value == null) {
			if (parameterAnnotation.required()) {
				throw new RuntimeException("Required parameter " + parameterAnnotation.name() + " not found.");
			}
			value = invocation.getParameter(parameterAnnotation.defaultValue());
		}

		// Try to parse the value to the correct type and set it to the field
		switch (field.getType().getName()) {
			case "java.lang.String" -> field.set(instance, value);
			case "int", "java.lang.Integer" -> field.set(instance, Integer.parseInt(value));
			case "long", "java.lang.Long" -> field.set(instance, Long.parseLong(value));
			case "float", "java.lang.Float" -> field.set(instance, Float.parseFloat(value));
			case "double", "java.lang.Double" -> field.set(instance, Double.parseDouble(value));
			default -> throw new RuntimeException("Unsupported parameter type " + field.getType().getName());
		}
	}


	/**
	 * Gets all classes in classpath that are annotated with @Command
	 *
	 * @param prefix The package to scan for commands.
	 */
	private List<Class<?>> getCommands(String prefix) {
		var reflections = new Reflections(prefix);
		return reflections
				.getTypesAnnotatedWith(Command.class)
				.stream()
				.filter(c -> {
					var name = c.getAnnotation(Command.class).name().toLowerCase();
					var shortName = c.getAnnotation(Command.class).shortname().toLowerCase();
					return !forbiddenCommands.contains(name) && !forbiddenCommands.contains(shortName);
				})
				.toList();
	}

}
