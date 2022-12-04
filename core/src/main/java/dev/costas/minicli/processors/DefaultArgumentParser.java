package dev.costas.minicli.processors;

import dev.costas.minicli.models.Invocation;

public class DefaultArgumentParser implements ArgumentParser {
	public DefaultArgumentParser() {
	}

	@Override
	public Invocation parse(String[] args) {
		var invocation = new Invocation();
		if (args.length == 0) {
			return invocation;
		}
		invocation.setCommand(args[0]);

		for (var i = 1; i < args.length; i++) {
			var arg = args[i].toLowerCase();
			// Only process flags/parameters if they start with a dash or two dashes (if not, they are the command to be called)
			if (arg.startsWith("-") || arg.startsWith("--")) {
				var key = arg.startsWith("--") ? arg.substring(2) : arg.substring(1);
				key = key.toLowerCase().trim();

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
		}
		return invocation;
	}
}