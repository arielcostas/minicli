package dev.costas.minicli.models;

public record Flag(
		boolean value
) implements Param {
	public Flag {
		value = true;
	}
}
