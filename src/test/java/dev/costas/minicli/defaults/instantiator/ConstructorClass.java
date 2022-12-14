package dev.costas.minicli.defaults.instantiator;

public class ConstructorClass {
	private String foo;

	public ConstructorClass(String foo) {
		this.foo = foo;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}
}
