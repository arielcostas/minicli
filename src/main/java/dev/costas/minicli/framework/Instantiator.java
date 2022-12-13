package dev.costas.minicli.framework;

/**
 * Interface for getting an instance of a class, via a default constructor, a factory or a DI container.
 */
public interface Instantiator {
	/**
	 * Gets an instance of the given class.
	 * @param classToInstantiate The class to instantiate.
	 * @return An instance of the given class.
	 * @param <T> The type of the class to instantiate.
	 */
	<T> T getInstance(Class<T> classToInstantiate);
}
