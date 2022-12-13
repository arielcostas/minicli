package dev.costas.minicli.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a call/invocation of a command, with the command name, parameters and flags.
 *
 * @since 1.0.0
 */
public class Invocation {
	/**
	 * The name of the command.
	 */
	private String command;
	/**
	 * The parameters of the command. A parameter is a key-value pair.
	 */
	private final Map<String, String> params;
	/**
	 * The flags of the command. A flag is a key-boolean pair, to enable or disable certain behaviour.
 	 */
	private final Map<String, Boolean> flags;

	/**
	 * Creates a new invocation with no command name and an empty set of parameters and flags.
	 */
	public Invocation() {
		this.command = null;
		this.params = new HashMap<>();
		this.flags = new HashMap<>();
	}

	/**
	 * Creates a new invocation with the given command name and an empty set of parameters and flags.
	 * @param command The name of the command to be invoked.
	 */
	public Invocation(String command) {
		this.command = command;
		this.params = new HashMap<>();
		this.flags = new HashMap<>();
	}

	/**
	 * Creates a new invocation with the given command name, parameters and flags.
	 * @param command The name of the command to be invoked.
	 * @param params The parameters of the command.
	 * @param flags The flags of the command.
	 */
	public Invocation(String command, Map<String, String> params, Map<String, Boolean> flags) {
		this.command = command;
		this.params = params;
		this.flags = flags;
	}

	/**
	 * Normalizes the provided string, by removing any leading and trailing whitespace and converting it to lowercase.
	 * @param str The string to be normalized.
	 * @return The normalized string.
	 */
	public static String normalize(String str) {
		return str.toLowerCase().trim();
	}

	/**
	 * Gets the name of the command to be invoked.
	 * @return The name of the command to be invoked.
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Sets the name of the command to be invoked.
	 * @param command The name of the command to be invoked.
	 */
	public void setCommand(String command) {
		this.command = normalize(command);
	}

	/**
	 * Gets the value of the flag with the given name.
	 * @param key The name of the flag to get.
	 * @return The value of the flag.
	 */
	public Boolean getFlag(String key) {
		return flags.getOrDefault(normalize(key), null);
	}

	/**
	 * Sets the value of the flag with the given name.
	 * @param key The name of the flag to set.
	 * @param value The value of the flag.
	 */
	public void putFlag(String key, Boolean value) {
		flags.put(key, value);
	}

	/**
	 * Gets the value of the parameter with the given name.
	 * @param key The name of the parameter to get.
	 * @return The value of the parameter.
	 */
	public String getParameter(String key) {
		return params.getOrDefault(normalize(key), null);
	}

	/**
	 * Sets the value of the parameter with the given name.
	 * @param key The name of the parameter to set.
	 * @param value The value of the parameter.
	 */
	public void putParameter(String key, String value) {
		params.put(key, value);
	}

	/**
	 * Gets the parameters of the command.
	 * @return The parameters of the command.
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * Gets the flags of the command.
	 * @return The flags of the command.
	 */
	public Map<String, Boolean> getFlags() {
		return flags;
	}
}
