package dev.costas.minicli.models;

public record Argument(
		String value
) implements Param {
	public Argument {
		value = "";
	}
}
