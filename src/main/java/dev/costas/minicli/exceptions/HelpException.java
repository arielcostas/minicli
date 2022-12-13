package dev.costas.minicli.exceptions;

/**
 * Represents an exception that is thrown when the user wants to quit the application.
 */
public class HelpException extends Exception {
	private final Class<?> clazz;

	/**
	 * Creates a new QuitException.
	 */
	public HelpException(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
