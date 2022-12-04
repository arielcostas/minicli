package dev.costas.minicli.processors;

import dev.costas.minicli.models.Invocation;

/**
 * Receives a class and an invocation and executes the command by injecting the parameters and flags, and calling the run method.
 */
public interface CommandExecutor {

	/**
	 * Executes the command by injecting the parameters and flags, and calling the run method.
	 * @param commandClass The class of the command to execute.
	 * @param args The invocation of the command.
	 */
	void execute(Class<?> commandClass, Invocation args);
}
