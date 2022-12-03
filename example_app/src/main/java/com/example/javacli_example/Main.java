package com.example.javacli_example;

import dev.costas.minicli.CliApplication;

public class Main {
	public static void main(String[] args) {
		CliApplication.withDefaults().run(Main.class, args);
	}
}