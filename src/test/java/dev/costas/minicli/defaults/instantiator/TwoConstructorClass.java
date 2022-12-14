package dev.costas.minicli.defaults.instantiator;

public class TwoConstructorClass {
	private String foo;

	public TwoConstructorClass() {
		foo = "bar";
	}

	public TwoConstructorClass(String foo) {
		this.foo = foo;
	}

	public String getFoo() {
		return foo;
	}
}
