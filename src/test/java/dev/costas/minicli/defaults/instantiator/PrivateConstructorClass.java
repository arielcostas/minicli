package dev.costas.minicli.defaults.instantiator;

public class PrivateConstructorClass {
	private String foo;

	private PrivateConstructorClass() {
		foo = "bar";
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}
}
