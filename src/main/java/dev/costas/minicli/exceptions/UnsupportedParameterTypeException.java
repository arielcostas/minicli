package dev.costas.minicli.exceptions;

public class UnsupportedParameterTypeException extends Exception {
	public UnsupportedParameterTypeException(String message) {
		super(message);
	}

	public UnsupportedParameterTypeException(String className, String fieldName) {
		this("The parameter type of " + className + "." + fieldName + " is not supported.");
	}
}
