package dev.costas.minicli.processors;

import java.io.OutputStream;
import java.util.List;

public interface HelpGenerator {
	void show(List<Class<?>> classes, OutputStream os, String separator);
}
