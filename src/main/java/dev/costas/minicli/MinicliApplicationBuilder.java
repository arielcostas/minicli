package dev.costas.minicli;

import dev.costas.minicli.models.ApplicationParams;
import dev.costas.minicli.processors.*;

import java.io.OutputStream;

/**
 * A builder for {@link MinicliApplication}.
 *
 * @since 1.0.0
 */
public class MinicliApplicationBuilder {
	private ArgumentParser argumentParser;
	private CommandExecutor commandExecutor;
	private HelpGenerator helpGenerator;
	private OutputStream outputStream;
	private ApplicationParams applicationParams;

	/**
	 * Creates a new builder with the default values.
	 */
	protected MinicliApplicationBuilder() {
		this.argumentParser = new DefaultArgumentParser();
		this.commandExecutor = new DefaultCommandExecutor();
		this.helpGenerator = new LinearHelpGenerator();
		this.applicationParams = null;
		this.outputStream = System.out;
	}

	/**
	 * Sets the application parameters used to provide help messages.
	 *
	 * @param application the application parameters
	 */
	public MinicliApplicationBuilder withApplicationParams(ApplicationParams application) {
		this.applicationParams = application;
		return this;
	}

	/**
	 * Sets the argument parser to use.
	 * @param argumentParser The argument parser to use.
	 * @return This builder with the argument parser set.
	 */
	public MinicliApplicationBuilder withArgumentParser(ArgumentParser argumentParser) {
		this.argumentParser = argumentParser;
		return this;
	}

	/**
	 * Sets the command executor to use.
	 * @param commandExecutor The command executor to use.
	 * @return This builder with the command executor set.
	 */
	public MinicliApplicationBuilder withCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
		return this;
	}

	/**
	 * Sets the help generator to use.
	 * @param helpGenerator The help generator to use.
	 * @return This builder with the help generator set.
	 */
	public MinicliApplicationBuilder withHelpGenerator(HelpGenerator helpGenerator) {
		this.helpGenerator = helpGenerator;
		return this;
	}

	/**
	 * Sets the output stream to use.
	 * @param outputStream The output stream to use.
	 * @return This builder with the output stream set.
	 */
	public MinicliApplicationBuilder withOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		return this;
	}

	/**
	 * Builds the {@link MinicliApplication}.
	 * @return The {@link MinicliApplication}.
	 */
	public MinicliApplication build() {
		return new MinicliApplication(argumentParser, commandExecutor, helpGenerator, outputStream, applicationParams);
	}
}
