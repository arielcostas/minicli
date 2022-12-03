package dev.costas.minicli.processors;

import dev.costas.minicli.models.Invocation;

public interface ArgumentParser {
	Invocation parse(String[] args);
}
