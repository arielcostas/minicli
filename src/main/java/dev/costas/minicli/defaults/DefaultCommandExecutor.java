package dev.costas.minicli.defaults;

import dev.costas.minicli.RunnableCommand;
import dev.costas.minicli.framework.CommandExecutor;
import dev.costas.minicli.models.CommandOutput;

/**
 * Default implementation of the {@link CommandExecutor} interface.
 *
 * @since 1.0.0
 */
public final class DefaultCommandExecutor implements CommandExecutor {
	/**
	 * Executes the command by injecting the parameters and flags, and calling the run method.
	 * <p>
	 * The output of the command is printed to the standard output.
	 *
	 * @param instance The command instance.
	 * @return The output of the command.
	 */
	public CommandOutput execute(RunnableCommand instance) {
		try {
			return instance.run();
		} catch (Exception e) {
			return new CommandOutput(false, e.getMessage());
		}
	}

}
