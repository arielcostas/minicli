package dev.costas.minicli.processors;

import dev.costas.minicli.annotation.Command;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class LinearHelpGenerator implements HelpGenerator {
	public static final int LEFT_MARGIN = 4;


	@Override
	public void show(List<Class<?>> classes, OutputStream os, String separator) {
		var ps = new PrintStream(os);
		ps.println("Usage: <command> [options] [arguments]");
		ps.println("===== Help =====");

		var spaces = " ".repeat(LEFT_MARGIN);

		for (var clazz : classes) {
			var command = clazz.getAnnotation(Command.class);
			ps.println(spaces + command.name() + separator + command.description());
		}
		ps.println(spaces + "help" + separator + "Shows this help");
	}
}
