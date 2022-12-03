package com.example.javacli_example;

import dev.costas.minicli.models.CommandOutput;
import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.annotation.Flag;
import dev.costas.minicli.annotation.Parameter;

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
