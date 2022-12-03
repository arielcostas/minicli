package dev.costas.javacli.args;

public record Argument(
		String value
) implements Param {
	public Argument {
		value = "";
	}
}
