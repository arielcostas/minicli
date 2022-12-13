package dev.costas.minicli.exceptions;

/**
 * Represents an exception that is thrown when the user wants to quit the application.
 */
public class QuitException extends Exception {
	/**
	 * Creates a new QuitException.
	 */
	public QuitException() {
		super();
	}

	/**
	 * Creates a new exception with the given message.
	 * @param message The message of the exception.
	 */
	public QuitException(String message) {
		super(message);
	}
}
