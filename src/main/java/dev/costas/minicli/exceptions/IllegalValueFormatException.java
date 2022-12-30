package dev.costas.minicli.exceptions;

/**
 * Thrown when the user passes an inconvertible value to a parameter. For example, a decimal value to an integer
 * parameter.
 */
public class IllegalValueFormatException extends Exception {
	private final String parameterName;
	private final String valuePassed;

	public IllegalValueFormatException(String parameterName, String valuePassed) {
		this.parameterName = parameterName;
		this.valuePassed = valuePassed;
	}
}
