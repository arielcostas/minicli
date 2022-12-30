# Minicli

A Java framework for easily building command-line applications using annotations.

[![GitHub](https://img.shields.io/github/license/arielcostas/minicli?color=blue&style=for-the-badge)](https://github.com/arielcostas/minicli/blob/main/LICENCE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.costas/minicli?style=for-the-badge)](https://search.maven.org/artifact/dev.costas/minicli)
[![Code Climate maintainability](https://img.shields.io/codeclimate/maintainability/arielcostas/minicli?label=Codeclimate&style=for-the-badge)](https://codeclimate.com/github/arielcostas/minicli/maintainability)
[![javadoc](https://javadoc.io/badge2/dev.costas/minicli/javadoc.svg?style=for-the-badge)](https://javadoc.io/doc/dev.costas/minicli)

Minicli (the "cli" is pronounced "clee") is a command-line application framework for Java. It provides a simple way to
create command-line applications with a consistent interface and a simple way to add new commands.

## Installation

Minicli is available on Maven Central. To use it, add the following dependency to your `pom.xml`:

```xml
<dependency>
	<groupId>dev.costas</groupId>
	<artifactId>minicli</artifactId>
	<version>VERSION</version>
</dependency>
```

If you're using Gradle, add the following dependency to your `build.gradle`:

```groovy
dependencies {
	/* ... */
	implementation 'dev.costas:minicli:VERSION'
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
public class GreetCommand implements RunnableCommand {
	@Parameter(name = "name", shortname = "n", description = "The name of the person to greet")
	private String name;
	
	@Parameter(name = "age", description = "The age of the greeted person")
	private int personAge;

	@Flag(name = "formal", description = "Whether to use a formal greeting")
	private boolean formal;

	public CommandOutput run() {
		if (formal) {
			return new CommandOutput("Good day, " + name + ". You're " + personAge + " years young.");
		} else {
			return new CommandOutput("Hey, " + name + ". You're " + personAge + " years young.");
		}
	}
}
```

This command can then be run from the command-line using the following command:

```bash
$ java -jar myapp.jar greet -n Ariel --formal --age 19
Good day, Ariel. You're 19 years young.
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

If you want to use this framework along with Guice or another dependency injection framework, you must replace the
Instantiator class with your own implementation. This is done by calling the `withInstantiator` method on the
`MinicliApplicationBuilder` class. For example:

```java
import dev.costas.minicli.framework.Instantiator;

public class GuiceInstantiator implements Instantiator {
	private final Injector injector;

	public GuiceInstantiator(Injector injector) {
		this.injector = injector;
	}

	@Override
	public <T> T instantiate(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
}
```

## Versioning

Minicli uses [Semantic Versioning](https://semver.org/). The version number is in the format `MAJOR.MINOR.PATCH`, where
`MAJOR` is incremented when a breaking change is made, `MINOR` is incremented when new features are added, or
non-breaking changes are made, and `PATCH` is incremented when bug fixes are made.

## Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue. If you want to contribute
code, please open a pull request.

## Licence

This project is licensed under the BSD 3-Clause License. See the [LICENCE](LICENCE) file for more details.

## Acknowledgements

This framework was created from scratch. It was slightly inspired by Spring Boot's REST controller annotations, but
otherwise has no relation with the Spring framework. While writing this document, it came to my attention that there is
another framework called [JCommander](https://jcommander.org/), which is also a command-line application framework for
Java. This project was not inspired by JCommander, but it is worth noting that it exists.

I'd like to thank [JetBrains](https://www.jetbrains.com/) for providing me with a free student license for their
IntelliJ IDEA IDE. This project was developed using IntelliJ IDEA. I'd also like to thank GitHub for their developer
student pack, which provided me with a free student license for GitHub Copilot, which is really useful for writing
documentation or boilerplate-y code.
