package dev.costas.javacli;

public record CommandOutput(
		boolean success,
		String output
) {
	public CommandOutput (String output) {
		this(true, output);
	}
}
