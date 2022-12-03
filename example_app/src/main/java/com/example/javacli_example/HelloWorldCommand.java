package com.example.javacli_example;

import dev.costas.javacli.CommandOutput;
import dev.costas.javacli.annotation.Command;
import dev.costas.javacli.annotation.Flag;
import dev.costas.javacli.annotation.Parameter;

import java.util.function.Supplier;

@Command(name = "hello", description = "Prints hello world")
public class HelloWorldCommand {
	@Parameter(name = "name", description = "The name to print", required = true)
	public String nameToGreet;

	@Flag(name = "formal", shortName = "f", description = "Use formal greeting")
	public boolean useFormalLanguage;

	public CommandOutput run() {
		if (useFormalLanguage) {
			return new CommandOutput("Good Morning, " + nameToGreet);
		}
		return new CommandOutput("Hello " + nameToGreet + "!");
	}
}
