package dev.costas.minicli.processors;

import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;
import dev.costas.minicli.models.CommandOutput;
import dev.costas.minicli.models.Invocation;

public class DefaultCommandExecutor implements CommandExecutor {
	public void execute(Class<?> commandClass, Invocation args) {
		try {
			var instance = commandClass.getDeclaredConstructor().newInstance();
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
							if (param.required()) {
								throw new RuntimeException("Missing required flag: " + param.name());
							}
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
			var result = (CommandOutput) commandClass.getMethod("run").invoke(instance);
			System.out.println(result.output());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}