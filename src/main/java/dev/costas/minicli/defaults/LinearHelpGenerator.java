package dev.costas.minicli.defaults;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.framework.HelpGenerator;
import dev.costas.minicli.models.ApplicationParams;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Generates the help text for the commands by printing the command name and description, one command per line.
 *
 * @since 1.0.0
 */
public final class LinearHelpGenerator implements HelpGenerator {
	private static final String SPACES = " ".repeat(4);
	private static final String SEPARATOR = " - ";

	public void show(ApplicationParams application, List<Class<?>> classes, OutputStream os) {
		var ps = new PrintStream(os);
		ps.println("\nUsage: <command> [options] [arguments]");
		ps.println("===== Help =====");

		for (var clazz : classes) {
			ps.println();
			this.showCommand(clazz, os);
		}

		ps.println();
		ps.println(SPACES + "h, help" + SEPARATOR + "Shows this help");
		ps.println(SPACES + "q, quit, exit" + SEPARATOR + "Exits the program");
	}

	@Override
	public void show(ApplicationParams application, Class<?> clazz, OutputStream os) {
		this.showCommand(clazz, os);
	}

	private void showCommand(Class<?> clazz, OutputStream os) {
		var ps = new PrintStream(os);

		var commandAnnotation = clazz.getAnnotation(Command.class);
		var parameters = this.getParameters(clazz);
		var flags = this.getFlags(clazz);

		printCommandName(commandAnnotation.name(), commandAnnotation.shortname(), commandAnnotation.description(), ps);
		printFlags(flags, ps);
		printParameters( parameters, ps);
	}

	private void printCommandName(String name, String shortName, String description, PrintStream ps) {
		var l = new StringBuilder(SPACES);
		l.append(name);
		if (!shortName.isEmpty()) {
			l.append(", ").append(shortName);
		}
		l.append(SEPARATOR).append(description);
		ps.println(l);
	}

	private void printFlags(List<Flag> flags, PrintStream ps) {
		if (flags.size() > 0) {
			ps.println(SPACES.repeat(2) + "Flags:");
			for (var flag : flags) {
				printOption(flag.name(), flag.shortName(), flag.description(), ps);
			}
		}
	}

	private void printParameters(List<Parameter> parameters, PrintStream ps) {
		if (parameters.size() > 0) {
			ps.println(SPACES.repeat(2) + "Parameters:");
			for (var parameter : parameters) {
				printOption(parameter.name(), parameter.shortName(), parameter.description(), ps);
			}
		}
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
