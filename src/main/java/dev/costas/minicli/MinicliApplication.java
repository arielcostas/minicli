package dev.costas.minicli;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.exceptions.QuitException;
import dev.costas.minicli.framework.ArgumentParser;
import dev.costas.minicli.framework.CommandExecutor;
import dev.costas.minicli.framework.HelpGenerator;
import dev.costas.minicli.framework.Instantiator;
import dev.costas.minicli.models.ApplicationParams;
import org.reflections.Reflections;

import java.io.OutputStream;
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

	protected MinicliApplication(ArgumentParser argumentParser, CommandExecutor commandExecutor, HelpGenerator helpGenerator, OutputStream outputStream, ApplicationParams application, Instantiator instantiator) {
		this.argumentParser = argumentParser;
		this.commandExecutor = commandExecutor;
		this.helpGenerator = helpGenerator;
		this.outputStream = outputStream;
		this.application = application;
		this.instantiator = instantiator;
	}

	public static MinicliApplicationBuilder builder() {
		return new MinicliApplicationBuilder();
	}

	/**
	 * Scans the given package for commands and executes the command with the given arguments.
	 *
	 * @param clazz The package to scan for commands. It also scans subpackages.
	 * @param args  The arguments to pass to the command.
	 */
	public void run(Class<?> clazz, String[] args) throws QuitException {
		var classes = getCommands(clazz.getPackageName());

		var invocation = argumentParser.parse(args);

		if (invocation.getCommand() == null || invocation.getCommand().isBlank() || invocation.getCommand().equals("help")) {
			this.helpGenerator.show(application, classes, outputStream);
			return;
		}

		if (invocation.getCommand().equals("quit") || invocation.getCommand().equals("exit")) {
			throw new QuitException();
		}

		var commandClass = classes.stream()
				.filter(c -> {
					var name = c.getAnnotation(Command.class).name().toLowerCase();
					var shortName = c.getAnnotation(Command.class).shortname().toLowerCase();
					return name.equals(invocation.getCommand()) || shortName.equals(invocation.getCommand());
				})
				.findFirst();

		if (commandClass.isEmpty()) {
			// TODO: Show an error message or throw, since the command is not found because of the developer's mistake.
			this.helpGenerator.show(application, classes, outputStream);
		} else {
			var instance = instantiator.getInstance(commandClass.get());
			if (!(instance instanceof RunnableCommand)) {
				throw new RuntimeException("Command class must implement RunnableCommand");
			}
			commandExecutor.execute((RunnableCommand) instance, invocation, outputStream);
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
