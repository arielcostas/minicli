package dev.costas.minicli.defaults.instantiator;

public class EmptyConstructorClass {
	private String foo;

	public EmptyConstructorClass() {
		foo = "bar";
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}
}
