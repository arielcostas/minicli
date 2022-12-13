package dev.costas.minicli.framework;

import dev.costas.minicli.RunnableCommand;
import dev.costas.minicli.models.CommandOutput;

/**
 * Receives a class and an invocation and executes the command by injecting the parameters and flags, and calling the run method.
 *
 * @since 1.0.0
 */
public interface CommandExecutor {
	/**
	 * Executes the command by injecting the parameters and flags, and calling the run method.
	 * <p>
	 * The output of the command is returned.
	 *
	 * @param instance The command instance.
	 * @return The output of the command.
	 */
	CommandOutput execute(RunnableCommand instance);
}