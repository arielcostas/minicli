package dev.costas.minicli.processors;

import dev.costas.minicli.models.Invocation;

public interface CommandExecutor {
	void execute(Class<?> commandClass, Invocation args);
}
