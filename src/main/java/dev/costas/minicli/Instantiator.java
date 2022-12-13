package dev.costas.minicli;

public interface Instantiator {
	<T> T getInstance(Class<T> classToInstantiate);
}
