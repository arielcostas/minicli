package dev.costas.minicli.defaults;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.framework.HelpGenerator;
import dev.costas.minicli.models.ApplicationParams;
import dev.costas.minicli.models.CommandOutput;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Generates the help text for the commands by printing the command name and description, one command per line.
 *
 * @since 1.0.0
 */
public final class LinearHelpGenerator implements HelpGenerator {
	private static final String SPACES = " ".repeat(4);
	private static final String SEPARATOR = " - ";

	public CommandOutput show(ApplicationParams application, List<Class<?>> classes) {
		var sb = new StringBuilder();
		sb.append("\nUsage: ").append(application.executable()).append(" [command] [options]\n");
		sb.append("===== Available commands =====").append("\n");

		for (var clazz : classes) {
			sb.append("\n").append(this.showCommand(clazz));
		}

		sb
			.append("\n")
			.append(SPACES).append("h, help").append(SEPARATOR).append("Shows this help").append("\n")
			.append(SPACES).append("q, quit").append(SEPARATOR).append("Quits the application").append("\n")
			.append(SPACES).append("v, version").append(SEPARATOR).append("Shows the application version").append("\n")
			.append("\n")
			.append(application.name()).append(" ").append(application.version()).append("\n");

		return new CommandOutput(true, sb.toString());
	}

	@Override
	public CommandOutput show(ApplicationParams application, Class<?> clazz) {
		return new CommandOutput(false, this.showCommand(clazz));
	}

	private String showCommand(Class<?> clazz) {
		var commandAnnotation = clazz.getAnnotation(Command.class);
		var parameters = this.getParameters(clazz);
		var flags = this.getFlags(clazz);

		var sb = new StringBuilder(SPACES);
		sb.append(commandAnnotation.name());
		if (!commandAnnotation.shortname().isEmpty()) {
			sb.append(", ").append(commandAnnotation.shortname());
		}
		sb.append(SEPARATOR).append(commandAnnotation.description());

		sb.append(printFlags(flags));
		sb.append(printParameters(parameters));

		return sb.toString();
	}

	private String printFlags(List<Flag> flags) {
		var sb = new StringBuilder();
		if (flags.size() > 0) {
			sb.append(SPACES.repeat(2)).append("Flags:").append("\n");
			for (var flag : flags) {
				sb.append(printOption(flag.name(), flag.shortName(), flag.description())).append("\n");
			}
		}
		return sb.toString();
	}

	private String printParameters(List<Parameter> parameters) {
		var sb = new StringBuilder();
		if (parameters.size() > 0) {
			sb.append(SPACES.repeat(2)).append("Parameters:").append("\n");
			for (var parameter : parameters) {
				sb.append(printOption(parameter.name(), parameter.shortName(), parameter.description())).append("\n");
			}
		}
		return sb.toString();
	}

	private String printOption(String name, String shortName, String description) {
		var line = new StringBuilder(SPACES.repeat(3));
		if (!shortName.equals("")) {
			line.append("-").append(shortName).append(", ");
		}
		line.append("--").append(name);
		line.append(SPACES).append(description);

		return line.toString();
	}

	private <T extends Annotation> List<T> getArgs(Class<?> clazz, Class<T> type) {
		return Arrays.stream(clazz.getDeclaredFields())
			.filter(f -> f.isAnnotationPresent(type))
			.sorted(Comparator.comparing(Field::getName))
			.map(f -> f.getAnnotation(type))
			.toList();
	}

	private List<Parameter> getParameters(Class<?> clazz) {
		return this.getArgs(clazz, Parameter.class);
	}

	private List<Flag> getFlags(Class<?> clazz) {
		return this.getArgs(clazz, Flag.class);
	}
}
