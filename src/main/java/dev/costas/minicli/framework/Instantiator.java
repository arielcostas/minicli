package dev.costas.minicli.framework;

/**
 * Interface for getting an instance of a class, via a default constructor, a factory or a DI container.
 */
public interface Instantiator {
	<T> T getInstance(Class<T> classToInstantiate);
}
