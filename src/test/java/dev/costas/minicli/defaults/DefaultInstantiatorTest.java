package dev.costas.minicli.defaults;

import dev.costas.minicli.defaults.instantiator.*;
import dev.costas.minicli.framework.Instantiator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultInstantiatorTest {
	public static final Instantiator instantiator = new DefaultInstantiator();

	@Test
	@DisplayName("Test that the default instantiator can instantiate a class")
	void getInstance() {
		var instance = instantiator.getInstance(EmptyClass.class);
		Assertions.assertNotNull(instance);
		Assertions.assertInstanceOf(EmptyClass.class, instance);
	}

	@Test
	@DisplayName("Test that the default instantiator can instantiate a class with a constructor")
	void getInstanceWithConstructor() {
		var instance = instantiator.getInstance(EmptyConstructorClass.class);
		Assertions.assertNotNull(instance);
		Assertions.assertInstanceOf(EmptyConstructorClass.class, instance);
	}

	@Test
	@DisplayName("Test that the default instantiator can't instantiate a class with constructor parameters")
	void getInstanceWithConstructorAndArguments() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			instantiator.getInstance(ConstructorClass.class);
		}, "Cannot instantiate class dev.costas.minicli.defaults.instantiator.ConstructorClass");
	}

	@Test
	@DisplayName("Test that the default instantiator fails to instantiate a private class")
	void getInstanceWithPrivateConstructor() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			instantiator.getInstance(PrivateConstructorClass.class);
		}, "Cannot instantiate class dev.costas.minicli.defaults.instantiator.PrivateConstructorClass");
	}

	@Test
	@DisplayName("Test that the default instantiator can instantiate a class with one constructor without parameters and one with parameters")
	void getInstanceWithMultipleConstructors() {
		var instance = instantiator.getInstance(TwoConstructorClass.class);
		Assertions.assertNotNull(instance);
		Assertions.assertInstanceOf(TwoConstructorClass.class, instance);
		Assertions.assertEquals("bar", instance.getFoo());
	}
}
