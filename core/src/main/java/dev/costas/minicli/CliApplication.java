package dev.costas.minicli;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.processors.*;
import org.reflections.Reflections;

import java.io.OutputStream;
import java.util.List;

public class CliApplication {
	private final ArgumentParser argumentParser;
	private final CommandExecutor commandExecutor;
	private final HelpGenerator helpGenerator;
	private final OutputStream outputStream;

	public CliApplication(ArgumentParser argumentParser, CommandExecutor commandExecutor, HelpGenerator helpGenerator, OutputStream outputStream) {
		this.argumentParser = argumentParser;
		this.commandExecutor = commandExecutor;
		this.helpGenerator = helpGenerator;
		this.outputStream = outputStream;
	}

	public static CliApplication withDefaults() {
		return new CliApplication(new DefaultArgumentParser(), new DefaultCommandExecutor(), new LinearHelpGenerator(), System.out);
	}

	public void run(Class<?> clazz, String[] args) {
		var classes = getCommands(clazz.getPackageName());

		ArgumentParser parser = new DefaultArgumentParser();
		var invocation = parser.parse(args);

		if (invocation.command == null || invocation.command.isBlank() || invocation.command.equals("help")) {
			this.helpGenerator.show(classes, outputStream);
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
			this.helpGenerator.show(classes, outputStream);
		} else {
			CommandExecutor executor = new DefaultCommandExecutor();
			executor.execute(commandClass.get(), invocation);
		}
	}

	/**
	 * Gets all classes in classpath that are annotated with @Command
	 */
	private static List<Class<?>> getCommands(String prefix) {
		var reflections = new Reflections(prefix);
		return reflections.getTypesAnnotatedWith(Command.class).stream().toList();
	}

}
