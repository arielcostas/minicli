package dev.costas.javacli;

public class ArgumentParser {
	public ArgumentParser() {
	}

	public Invocation parse(String[] args) {
		var invocation = new Invocation(args[0]);
		for (var i = 1; i < args.length; i++) {
			var arg = args[i].toLowerCase();
			// Only process flags/parameters if they start with a dash or two dashes (if not, they are the command to be called)
			if (arg.startsWith("-") || arg.startsWith("--")) {
				// TODO: Lowercast, trims and all that in Invocation class
				var key = arg.startsWith("--") ? arg.substring(2) : arg.substring(1);
				key = key.toLowerCase().trim();
				String value;
				if (args.length > i+1) {
					value = args[i + 1];
				} else {
					value = "";
				}

				value = value.toLowerCase().trim();

				if (value.startsWith("-") || value.startsWith("--")) {
					invocation.putFlag(key, true);
				} else if (value.equals("true")) {
					invocation.putFlag(key, true);
					i++;
				} else if (value.equals("false")) {
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
