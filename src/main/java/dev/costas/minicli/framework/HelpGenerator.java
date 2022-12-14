package dev.costas.minicli.framework;

import dev.costas.minicli.models.ApplicationParams;
import dev.costas.minicli.models.CommandOutput;

import java.util.List;

/**
 * Generates the help text for the commands.
 *
 * @since 1.0.0
 */
public interface HelpGenerator {
	/**
	 * Generates the help text for the commands.
	 * @param application The application parameters.
	 * @param classes The classes of the commands to generate the help text for.
	 */
	CommandOutput show(ApplicationParams application, List<Class<?>> classes);

	/**
	 * Generates the help text for a single command.
	 * @param application The application parameters.
	 * @param clazz The class of the command to generate the help text for.
	 */
	CommandOutput show(ApplicationParams application, Class<?> clazz);
}
