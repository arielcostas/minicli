package dev.costas.minicli.defaults;

import dev.costas.minicli.framework.ArgumentParser;
import dev.costas.minicli.models.Invocation;

/**
 * Default argument parser implementation.
 *
 * @since 1.0.0
 */
public final class DefaultArgumentParser implements ArgumentParser {
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

			var key = arg.startsWith("--") ? arg.substring(2) : arg.substring(1);
			key = Invocation.normalize(key);

			String value;
			if (args.length > i+1) {
				value = args[i + 1];
			} else {
				value = "";
			}

			var normalizedValue = value.toLowerCase().trim();

			if (value.startsWith("-") || value.startsWith("--")) {
				invocation.putFlag(key, true);
			} else if (normalizedValue.equals("true")) {
				invocation.putFlag(key, true);
				i++;
			} else if (normalizedValue.equals("false")) {
				invocation.putFlag(key, false);
				i++;
			} else {
				invocation.putParameter(key, value);
				i++;
			}
		}
		return invocation;
	}
}
