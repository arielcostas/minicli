# Minicli

A Java framework for easily building command-line applications using annotations.

![GitHub](https://img.shields.io/github/license/arielcostas/minicli?color=blue&style=for-the-badge)
![Maven Central](https://img.shields.io/maven-central/v/dev.costas/minicli?style=for-the-badge)
![Code Climate maintainability](https://img.shields.io/codeclimate/maintainability/arielcostas/minicli?label=Codeclimate&style=for-the-badge)
[![javadoc](https://javadoc.io/badge2/dev.costas/minicli/javadoc.svg?style=for-the-badge)](https://javadoc.io/doc/dev.costas/minicli)

Minicli (the "cli" is pronounced "clee") is a command-line application framework for Java. It provides a simple way to
create command-line applications with a consistent interface and a simple way to add new commands.

## Installation

Minicli is available on Maven Central. To use it, add the following dependency to your `pom.xml`:

```xml

<dependency>
	<groupId>dev.costas</groupId>
	<artifactId>minicli</artifactId>
	<version>1.0.0</version>
</dependency>
```

If you're using Gradle, add the following dependency to your `build.gradle`:

```groovy
dependencies {
	/* ... */
	implementation 'dev.costas:minicli:1.0.0'
}
```

## Usage

Minicli is designed to be as simple as possible to use. It works by scanning your application for classes annotated with
`@Command` and then creating a command-line interface based on those commands. To use it, simply annotate your classes
with `@Command` and declare arguments (which accept a value) and flags (which are boolean values) using the `@Argument`
and
`@Flag` annotations. For example:

```java
import dev.costas.minicli.models.CommandOutput;

@Command(name = "greet", description = "Prints a greeting")
public class GreetCommand {
	@Argument(name = "name", shortname = "n", description = "The name of the person to greet")
	private String name;

	@Flag(name = "formal", description = "Whether to use a formal greeting")
	private boolean formal;

	@OnInvoke
	public CommandOutput run() {
		if (formal) {
			return new CommandOutput("Good day, " + name);
		} else {
			return new CommandOutput("Hey, " + name);
		}
	}
}
```

This command can then be run from the command-line using the following command:

```bash
$ java -jar myapp.jar greet -n Ariel --formal
Good day, Ariel
```

Note that, as for now, arguments may only be strings and flags may only be booleans. In the future, numbers and other
argument types may be supported. Also, note that the `@OnInvoke` annotation is required for the command to be run, and
must be used on a method that returns a `CommandOutput` object.

### Initialisation

Minicli needs to be initialised before it can be used. This is done on the main class of your application, which
instantiates a `MinicliApplication` via the `MinicliApplicationBuilder` class, which is used to configure the
application.

```java
import dev.costas.minicli.MinicliApplication;
import dev.costas.minicli.models.ApplicationParams;

public class Greetinator {
	public static void main(String[] args) {
		ApplicationParams params = new ApplicationParams(
				"Greetinator",
				"java -jar greetinator",
				"A command-line application for greeting platypuses and other secret animal agents, but it also works for humans",
				"1.2.3",
				"Doofenshmirtz Evil Inc.",
				"hello@example.com",
				"https://example.com"
		);

		MinicliApplication app = MinicliApplication.builder()
				.withApplicationParams(params)
				.build();
		app.run(args);
	}
}
```

The `ApplicationParams` class is used to configure the application's name, version, author, etc. Some values are
probably constant but others may change depending on the application. For example, the version number may be read from a
file inside the classpath or from a system property.

The `MinicliApplication` class is used to run the application. It takes an array of strings as an argument, which
should be the command-line arguments passed to the application. The `run` method will then parse the arguments and
execute the appropriate command.

## Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue. If you want to contribute
code, please open a pull request.

## Licence

This project is licensed under the BSD 3-Clause License. See the [LICENCE](LICENCE) file for more details.