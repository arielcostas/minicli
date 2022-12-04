package com.example.javacli_example.command;

import dev.costas.minicli.annotation.Command;
import dev.costas.minicli.models.CommandOutput;

@Command(name = "goodbye", description = "Prints goodbye world")
public class GoodbyeWorld {
	public CommandOutput run() {
		return new CommandOutput("Goodbye!");
	}
}
