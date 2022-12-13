package dev.costas.minicli.defaults;

import dev.costas.minicli.framework.ArgumentParser;
import dev.costas.minicli.models.Invocation;

/**
 * Default argument parser implementation.
 *
 * @since 1.0.0
 */
public final class DefaultArgumentParser implements ArgumentParser {
	/**
	 * Creates a new instance of the default argument parser.
	 */
	public DefaultArgumentParser() {
	}

	/**
	 * Parses the arguments and returns an invocation object.
	 * @param args the arguments to parse, including the name of the command
	 * @return the invocation object
	 */
	@Override
	public Invocation parse(String[] args) {
		var invocation = new Invocation();
		if (args.length == 0) {
			return invocation;
		}
		invocation.setCommand(args[0]);

		for (var i = 1; i < args.length; i++) {
			var arg = args[i].toLowerCase();

			if (!arg.startsWith("-") && !arg.startsWith("--")) {
				continue;
			}

			var next = i + 1 < args.length ? args[i + 1].toLowerCase() : "";

			parseArg(arg, next, invocation);
		}
		return invocation;
	}

	private void parseArg(String arg, String value, Invocation invocation) {
		var key = arg.startsWith("--") ? arg.substring(2) : arg.substring(1);
		key = Invocation.normalize(key);

		var normalizedValue = value.toLowerCase().trim();

		if (value.startsWith("-") || value.startsWith("--") || value.equals("")) {
			// If the next thing is an argument or is nothing, then the current argument is a flag and is true for being present
			invocation.putFlag(key, true);
		} else if (normalizedValue.equals("true")) {
			// If the next thing is "true", then the current argument is a flag and must be set to true
			invocation.putFlag(key, true);
		} else if (normalizedValue.equals("false")) {
			// If the next thing is "false", then the current argument is a flag and must be set to false
			invocation.putFlag(key, false);
		} else {
			// If neither, then the current argument is a parameter and the next thing is its value
			invocation.putParameter(key, value);
		}
	}
}
