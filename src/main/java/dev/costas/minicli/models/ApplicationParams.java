package dev.costas.minicli.models;

/**
 * Application parameters, such as the name of the application, the version, the author, etc.
 *
 * @since 1.0.0
 */
public final class ApplicationParams {
	/**
	 * The name of the application. For example `FooApp`.
	 */
	private final String name;
	/**
	 * The name of the executable file. For eample, `myapp.exe` or `java -jar myapp.jar`.
	 */
	private final String executable;
	/**
	 * A short description of the application or what it does.
	 */
	private final String description;
	/**
	 * The version of the application.
	 */
	private final String version;
	/**
	 * The author(s) or copyright holder(s) of the application.
	 */
	private final String author;
	/**
	 * An email address where the user can contact the author(s) or the maintainer(s) of the application.
	 */
	private final String supportEmail;
	/**
	 * The URL of the website of the application.
	 */
	private final String website;

	public ApplicationParams(String name, String executable, String description, String version, String author, String supportEmail, String website) {
		this.name = name;
		this.executable = executable;
		this.description = description;
		this.version = version;
		this.author = author;
		this.supportEmail = supportEmail;
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public String getExecutable() {
		return executable;
	}

	public String getDescription() {
		return description;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

	public String getSupportEmail() {
		return supportEmail;
	}

	public String getWebsite() {
		return website;
	}
}
