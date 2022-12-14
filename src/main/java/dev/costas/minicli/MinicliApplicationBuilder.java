package dev.costas.minicli;

import dev.costas.minicli.defaults.DefaultCommandExecutor;
import dev.costas.minicli.defaults.DefaultInstantiator;
import dev.costas.minicli.defaults.LinearHelpGenerator;
import dev.costas.minicli.framework.CommandExecutor;
import dev.costas.minicli.framework.HelpGenerator;
import dev.costas.minicli.framework.Instantiator;
import dev.costas.minicli.models.ApplicationParams;

/**
 * A builder for {@link MinicliApplication}.
 *
 * @since 1.0.0
 */
public class MinicliApplicationBuilder {
	private CommandExecutor commandExecutor;
	private HelpGenerator helpGenerator;
	private ApplicationParams applicationParams;
	private Instantiator instantiator;

	/**
	 * Creates a new builder with the default values.
	 */
	protected MinicliApplicationBuilder() {
		this.commandExecutor = new DefaultCommandExecutor();
		this.helpGenerator = new LinearHelpGenerator();
		this.applicationParams = null;
		this.instantiator = new DefaultInstantiator();
	}

	/**
	 * Sets the application parameters used to provide help messages.
	 *
	 * @param application the application parameters
	 * @return This builder with the application parameters set
	 */
	public MinicliApplicationBuilder withApplicationParams(ApplicationParams application) {
		this.applicationParams = application;
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
	 * Sets the instantiator to use.
	 * @param instantiator The instantiator to use.
	 * @return This builder with the instantiator set.
	 */
	public MinicliApplicationBuilder withInstantiator(Instantiator instantiator) {
		this.instantiator = instantiator;
		return this;
	}

	/**
	 * Builds the {@link MinicliApplication}.
	 * @return The {@link MinicliApplication}.
	 */
	public MinicliApplication build() {
		return new MinicliApplication(commandExecutor, helpGenerator, applicationParams, instantiator);
	}
}
