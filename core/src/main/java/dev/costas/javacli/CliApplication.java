package dev.costas.javacli;

import dev.costas.javacli.annotation.Command;
import dev.costas.javacli.annotation.Flag;
import dev.costas.javacli.annotation.Parameter;
import org.reflections.Reflections;

import java.util.Set;

public class CliApplication {
	public static void run(Class<?> clazz, String[] args) {
		var classes = getCommands(clazz.getPackageName());

		ArgumentParser parser = new ArgumentParser();
		var invocation = parser.parse(args);

		if (invocation.command == null || invocation.command.isBlank() || invocation.command.equals("help")) {
			showHelp(classes);
			return;
		}

		var commandClass = classes.stream()
				.filter(c -> {
					var name = c.getAnnotation(Command.class).name().toLowerCase();
					var shortName = c.getAnnotation(Command.class).shortname().toLowerCase();
					return name.equals(invocation.command) || shortName.equals(invocation.command);
				})
				.findFirst();

		if (commandClass.isEmpty()) {
			showHelp(classes);
		} else {
			executeCommand(commandClass.get(), invocation);
		}
	}

	private static void executeCommand(Class<?> commandClass, Invocation args) {
		try {
			var instance = commandClass.getDeclaredConstructor().newInstance();
			var fields = instance.getClass().getDeclaredFields();
			for (var field : fields) {
				var annotations = field.getAnnotations();
				if (annotations.length != 1) {
					continue;
				}

				if (annotations[0] instanceof Flag param) {
					var value = args.getFlag(param.name());
					if (value == null) {
						value = args.getFlag(param.shortName());
						if (value == null) {
							if (param.required()) {
								throw new RuntimeException("Missing required flag: " + param.name());
							}
							value = param.defaultValue();
						}
					}
					field.setAccessible(true);
					field.set(instance, value);
				} else if (annotations[0] instanceof Parameter param) {
					var value = args.getParameter(param.name());
					if (value == null) {
						value = args.getParameter(param.shortName());
						if (value == null) {
							if (param.required()) {
								throw new RuntimeException("Missing required parameter: " + param.name());
							}
							value = param.defaultValue();
						}
					}
					field.setAccessible(true);
					field.set(instance, value);
				}
			}
			var result = (CommandOutput) commandClass.getMethod("run").invoke(instance);
			System.out.println(result.output());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets all classes in classpath that are annotated with @Command
	 */
	private static Set<Class<?>> getCommands(String prefix) {
		var reflections = new Reflections(prefix);
		return reflections.getTypesAnnotatedWith(Command.class);
	}

	private static void showHelp(Set<Class<?>> classes) {
		System.out.println("===== Help =====");
		for (var clazz : classes) {
			var command = clazz.getAnnotation(Command.class);
			System.out.println(command.name() + " - " + command.description());
		}
	}
}
