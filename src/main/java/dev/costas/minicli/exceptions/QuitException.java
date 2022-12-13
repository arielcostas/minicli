package dev.costas.minicli.exceptions;

public class QuitException extends Exception {
	public QuitException() {
		super();
	}

	public QuitException(String message) {
		super(message);
	}
}
