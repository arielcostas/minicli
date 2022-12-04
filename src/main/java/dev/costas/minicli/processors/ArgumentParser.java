package dev.costas.minicli.processors;

import dev.costas.minicli.models.Invocation;

/**
 * Parses the arguments and returns an invocation object.
 */
public interface ArgumentParser {

	/**
	 * Parses the arguments and returns an invocation object.
	 *
	 * @param args the arguments to parse, including the name of the command
	 * @return the invocation object
	 */
	Invocation parse(String[] args);
}
