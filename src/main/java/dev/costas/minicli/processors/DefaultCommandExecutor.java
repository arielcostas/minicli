package dev.costas.minicli.processors;

import dev.costas.minicli.RunnableCommand;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.OnInvoke;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.models.CommandOutput;
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
	 * @param args The invocation of the command.
	 */
	public void execute(RunnableCommand instance, Invocation args, PrintStream out) {
		try {
			var fields = instance.getClass().getDeclaredFields();
			for (var field : fields) {
				var annotations = field.getAnnotations();
				if (annotations.length != 1) {
					continue;
				}

				if (annotations[0] instanceof Flag param) {
					var value = args.getFlag(param.name());
					if (value == null) {
						value = args.getFlag(param.shortName());
						if (value == null) {
							value = param.defaultValue();
						}
					}
					field.setAccessible(true);
					field.set(instance, value);
				} else if (annotations[0] instanceof Parameter param) {
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

			var result = instance.run();
			out.println(result.output());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
