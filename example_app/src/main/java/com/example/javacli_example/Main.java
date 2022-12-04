package com.example.javacli_example;

import dev.costas.minicli.MinicliApplication;
import dev.costas.minicli.MinicliApplicationBuilder;

import java.net.http.HttpClient;

public class Main {
	public static void main(String[] args) {
		var app = MinicliApplication
				.builder()

				.build();
		app.run(Main.class, args);
	}
}