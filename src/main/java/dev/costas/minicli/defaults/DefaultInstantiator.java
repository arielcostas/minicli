package dev.costas.minicli.defaults;

import dev.costas.minicli.framework.Instantiator;

import java.lang.reflect.InvocationTargetException;

/**
 * Default implementation of the {@link Instantiator} interface.
 * <p>
 * It uses a constructor without parameters to instantiate the class.
 *
 * @since 2.0.0
 */
public final class DefaultInstantiator implements Instantiator {
	@Override
	public <T> T getInstance(Class<T> classToInstantiate) {

		try {
			return classToInstantiate.getConstructor().newInstance();
		} catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
				 IllegalAccessException e) {
			throw new RuntimeException("Cannot instantiate class " + classToInstantiate.getName(), e);
		}

	}
}
