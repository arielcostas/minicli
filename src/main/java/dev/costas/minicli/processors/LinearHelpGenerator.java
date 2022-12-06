package dev.costas.minicli.processors;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.models.ApplicationParams;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Generates the help text for the commands by printing the command name and description, one command per line.
 *
 * @since 1.0.0
 */
public class LinearHelpGenerator implements HelpGenerator {
	public static final String SPACES = " ".repeat(4);
	public static final String SEPARATOR = " - ";

	public void show(ApplicationParams application, List<Class<?>> classes, OutputStream os) {
		var ps = new PrintStream(os);
		ps.println("Usage: <command> [options] [arguments]");
		ps.println("===== Help =====");

		for (var clazz : classes) {
			ps.println();
			var command = clazz.getAnnotation(Command.class);
			var parameters = this.getParameters(clazz);
			var flags = this.getFlags(clazz);

			var l = new StringBuilder(SPACES);
			l.append(command.name());
			if (!command.shortname().isEmpty()) {
				l.append(", ").append(command.shortname());
			}
			l.append(SEPARATOR).append(command.description());
			ps.println(l);

			ps.println(SPACES.repeat(2) + "Parameters:");
			for (var parameter : parameters) {
				printOption(parameter.name(), parameter.shortName(), parameter.description(), ps);
			}
			ps.println(SPACES.repeat(2) + "Flags:");
			for (var flag : flags) {
				printOption(flag.name(), flag.shortName(), flag.description(), ps);
			}
		}
		ps.println();
		ps.println(SPACES + "help" + SEPARATOR + "Shows this help");
	}

	private void printOption(String name, String shortName, String description, PrintStream ps) {
		var line = new StringBuilder(SPACES.repeat(3));
		if (!shortName.equals("")) {
			line.append("-").append(shortName).append(", ");
		}
		line.append("--").append(name);
		line.append(SPACES).append(description);

		ps.println(line);
	}

	private List<Parameter> getParameters(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(Parameter.class))
				.sorted(Comparator.comparing(Field::getName))
				.map(f -> f.getAnnotation(Parameter.class))
				.toList();
	}

	private List<Flag> getFlags(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(Flag.class))
				.sorted(Comparator.comparing(Field::getName))
				.map(f -> f.getAnnotation(Flag.class))
				.toList();
	}
}
