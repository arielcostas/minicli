package dev.costas.minicli.processors;

import dev.costas.minicli.RunnableCommand;
import dev.costas.minicli.models.Invocation;

import java.io.PrintStream;

/**
 * Receives a class and an invocation and executes the command by injecting the parameters and flags, and calling the run method.
 *
 * @since 1.0.0
 */
public interface CommandExecutor {
	/**
	 * Executes the command by injecting the parameters and flags, and calling the run method.
	 * <p>
	 * The output of the command is printed to the standard output.
	 *
	 * @param instance The command instance.
	 * @param args The invocation of the command.
	 * @param out The output PrintStream to print the output of the command.
	 */
	void execute(RunnableCommand instance, Invocation args, PrintStream out);
}