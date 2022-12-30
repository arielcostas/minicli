package dev.costas.minicli;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.exceptions.HelpException;
import dev.costas.minicli.exceptions.IllegalValueFormatException;
import dev.costas.minicli.exceptions.QuitException;
import dev.costas.minicli.exceptions.UnsupportedParameterTypeException;
import dev.costas.minicli.framework.CommandExecutor;
import dev.costas.minicli.framework.HelpGenerator;
import dev.costas.minicli.framework.Instantiator;
import dev.costas.minicli.models.ApplicationParams;
import dev.costas.minicli.models.CommandOutput;
import org.reflections.Reflections;

import java.util.List;

/**
 * The main class of the application.
 *
 * @since 1.0.0
 */
public class MinicliApplication {
	private final CommandExecutor commandExecutor;
	private final HelpGenerator helpGenerator;
	private final ApplicationParams application;
	private final Instantiator instantiator;

	/**
	 * Creates a new instance of the application.
	 *
	 * @param commandExecutor The command executor to use.
	 * @param helpGenerator   The help generator to use.
	 * @param application     The application parameters.
	 * @param instantiator    The instantiator to use.
	 */
	protected MinicliApplication(CommandExecutor commandExecutor, HelpGenerator helpGenerator, ApplicationParams application, Instantiator instantiator) {
		this.commandExecutor = commandExecutor;
		this.helpGenerator = helpGenerator;
		this.application = application;
		this.instantiator = instantiator;
	}

	/**
	 * Gets an instance of the application builder.
	 *
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
	 * @return The output of the command.
	 * @throws QuitException                     If the user wants to quit the application.
	 * @throws UnsupportedParameterTypeException If a command parameter has an unsupported type.
	 */
	public CommandOutput run(Class<?> clazz, String[] args) throws QuitException, UnsupportedParameterTypeException, IllegalValueFormatException {
		try {
			return actuallyRun(clazz, args);
		} catch (IllegalAccessException e) {
			return new CommandOutput(false, e.getMessage());
		}
	}

	private CommandOutput actuallyRun(Class<?> clazz, String[] args) throws QuitException, IllegalAccessException, UnsupportedParameterTypeException, IllegalValueFormatException {
		var classes = getCommands(clazz.getPackageName());
		if (args.length == 0) {
			return this.helpGenerator.show(application, classes);
		}

		if (args[0].equals("v") || args[0].equals("version")) {
			return new CommandOutput(true, application.formatted());
		}

		if (args[0].equals("quit") || args[0].equals("q") || args[0].equals("exit")) {
			throw new QuitException();
		}

		var candidades = getCandidates(args[0], classes);

		if (args[0].equals("h") || args[0].equals("help")) {
			if (args.length == 2) {
				candidades = getCandidates(args[1], classes);
				return this.helpGenerator.show(application, candidades.get(0));
			}
			return this.helpGenerator.show(application, classes);
		}

		switch (candidades.size()) {
			case 0 -> throw new RuntimeException("Command not found.");
			case 1 -> {
				var instance = instantiator.getInstance(candidades.get(0));

				if (instance instanceof RunnableCommand runnableInstance) {
					try {
						Inflater.inflateInstance(runnableInstance, args);
					} catch (HelpException e) {
						return this.helpGenerator.show(application, e.getClazz());
					}
				} else {
					throw new RuntimeException("Command class must implement RunnableCommand");
				}
				return commandExecutor.execute((RunnableCommand) instance);
			}
			default -> throw new RuntimeException("Multiple commands with the same name found.");
		}
	}


	/**
	 * Gets all classes in classpath that are annotated with @Command
	 *
	 * @param prefix The package to scan for commands.
	 */
	private List<Class<?>> getCommands(String prefix) {
		var reflections = new Reflections(prefix);
		final List<String> forbiddenCommands = List.of("h", "help", "q", "quit", "exit", "v", "version");

		return reflections.getTypesAnnotatedWith(Command.class).stream().filter(c -> {
			var name = c.getAnnotation(Command.class).name().toLowerCase();
			var shortname = c.getAnnotation(Command.class).shortname().toLowerCase();
			return !forbiddenCommands.contains(name) && !forbiddenCommands.contains(shortname);
		}).toList();
	}

	/**
	 * Gets the command class that matches the given name.
	 */
	private List<Class<?>> getCandidates(String arg, List<Class<?>> classes) {
		return classes.stream().filter(c -> {
			var name = c.getAnnotation(Command.class).name().toLowerCase();
			var shortname = c.getAnnotation(Command.class).shortname().toLowerCase();
			return name.equals(arg) || shortname.equals(arg);
		}).toList();
	}
}
