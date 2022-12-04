package dev.costas.minicli.models;

/**
 * Represents the output of a command.
 * @param success Whether the command was successful or not.
 * @param output The output of the command.
 */
public record CommandOutput(
		boolean success,
		String output
) {
	public CommandOutput (String output) {
		this(true, output);
	}
}
