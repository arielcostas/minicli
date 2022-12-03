package dev.costas.minicli.models;

import java.util.HashMap;
import java.util.Map;

public class Invocation {
	public String command;
	private Map<String, String> params;
	private Map<String, Boolean> flags;

	public Invocation() {
	}

	public Invocation(String command) {
		this.command = command;
		this.params = new HashMap<>();
		this.flags = new HashMap<>();
	}

	public Invocation(String command, Map<String, String> params, Map<String, Boolean> flags) {
		this.command = command;
		this.params = params;
		this.flags = flags;
	}

	private String normalize(String str) {
		return str.toLowerCase().trim();
	}

	public void setCommand(String command) {
		this.command = normalize(command);
	}

	public Boolean getFlag(String key) {
		return flags.getOrDefault(normalize(key), null);
	}

	public void putFlag(String key, Boolean value) {
		flags.put(key, value);
	}

	public String getParameter(String key) {
		return params.getOrDefault(normalize(key), null);
	}

	public void putParameter(String key, String value) {
		params.put(key, value);
	}
}
