package dev.costas.minicli.processors;

import dev.costas.minicli.annotation.Command;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Generates the help text for the commands by printing the command name and description, one command per line.
 */
public class LinearHelpGenerator implements HelpGenerator {
	public static final int LEFT_MARGIN = 4;
	public static final String SEPARATOR = " - ";

	public void show(List<Class<?>> classes, OutputStream os) {
		var ps = new PrintStream(os);
		ps.println("Usage: <command> [options] [arguments]");
		ps.println("===== Help =====");

		var spaces = " ".repeat(LEFT_MARGIN);

		for (var clazz : classes) {
			var command = clazz.getAnnotation(Command.class);
			ps.println(spaces + command.name() + SEPARATOR + command.description());
		}
		ps.println(spaces + "help" + SEPARATOR + "Shows this help");
	}
}
