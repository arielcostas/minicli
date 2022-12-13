package dev.costas.minicli.models;

/**
 * Application parameters, such as the name of the application, the version, the author, etc.
 *
 * @since 1.0.0
 */
public record ApplicationParams(
		String name,
		String executable,
		String description,
		String version,
		String author,
		String supportEmail,
		String website
) {
}
