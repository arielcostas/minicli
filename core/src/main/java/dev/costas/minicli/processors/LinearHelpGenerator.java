package dev.costas.minicli.processors;

import dev.costas.minicli.annotation.Command;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class LinearHelpGenerator implements HelpGenerator {
	@Override
	public void show(List<Class<?>> classes, OutputStream os) {
		var ps = new PrintStream(os);
		ps.println("===== Help =====");
		for (var clazz : classes) {
			var command = clazz.getAnnotation(Command.class);
			ps.println(command.name() + " - " + command.description());
		}
	}
}
