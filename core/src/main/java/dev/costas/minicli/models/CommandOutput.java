package dev.costas.minicli.models;

public record CommandOutput(
		boolean success,
		String output
) {
	public CommandOutput (String output) {
		this(true, output);
	}
}
