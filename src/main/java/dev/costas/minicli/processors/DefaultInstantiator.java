package dev.costas.minicli.processors;

import dev.costas.minicli.Instantiator;

import java.lang.reflect.InvocationTargetException;

public class DefaultInstantiator implements Instantiator {
	@Override
	public <T> T getInstance(Class<T> classToInstantiate) {

		try {
			return classToInstantiate.getConstructor().newInstance();
		} catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Cannot instantiate class " + classToInstantiate.getName(), e);
		}

	}
}
