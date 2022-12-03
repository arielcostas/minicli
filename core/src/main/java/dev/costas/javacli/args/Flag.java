package dev.costas.javacli.args;

public record Flag(
		boolean value
) implements Param {
	public Flag {
		value = true;
	}
}
