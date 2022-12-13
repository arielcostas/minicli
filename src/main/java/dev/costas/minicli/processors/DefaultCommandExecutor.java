package dev.costas.minicli.processors;

import dev.costas.minicli.RunnableCommand;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.models.Invocation;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Default implementation of the {@link CommandExecutor} interface.
 *
 * @since 1.0.0
 */
public class DefaultCommandExecutor implements CommandExecutor {
	/**
	 * Executes the command by injecting the parameters and flags, and calling the run method.
	 * <p>
	 * The output of the command is printed to the standard output.
	 *
	 * @param instance The command instance.
	 * @param args     The invocation of the command.
	 */
	public void execute(RunnableCommand instance, Invocation args, PrintStream out) {
		try {
			injectArguments(instance, args);

			var result = instance.run();
			out.println(result.output());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void injectArguments(RunnableCommand instance, Invocation args) throws IllegalAccessException {
		var fields = instance.getClass().getDeclaredFields();
		for (var field : fields) {
			var annotations = field.getAnnotations();
			if (annotations.length != 1) {
				continue;
			}

			var annotation = Arrays.stream(annotations)
					.filter(a -> a instanceof Parameter || a instanceof Flag)
					.findFirst()
					.orElse(null);

			if (annotation == null) {
				continue;
			}

			if (annotation instanceof Flag flag) {
				var value = args.getFlag(flag.name());
				if (value == null) {
					value = args.getFlag(flag.shortName());
					if (value == null) {
						value = flag.defaultValue();
					}
				}
				field.setAccessible(true);
				field.set(instance, value);
			} else if (annotation instanceof Parameter param) {
				var value = args.getParameter(param.name());
				if (value == null) {
					value = args.getParameter(param.shortName());
					if (value == null) {
						if (param.required()) {
							throw new RuntimeException("Missing required parameter: " + param.name());
						}
						value = param.defaultValue();
					}
				}
				field.setAccessible(true);
				field.set(instance, value);
			}
		}
	}
}
