package dev.costas.minicli.framework;

import dev.costas.minicli.models.ApplicationParams;

import java.io.OutputStream;
import java.util.List;

/**
 * Generates the help text for the commands.
 *
 * @since 1.0.0
 */
public interface HelpGenerator {
	/**
	 * Generates the help text for the commands.
	 * @param classes The classes of the commands to generate the help text for.
	 * @param os The output stream to write the help text to.
	 */
	void show(ApplicationParams application, List<Class<?>> classes, OutputStream os);
}
