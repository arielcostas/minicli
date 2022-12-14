package dev.costas.minicli.defaults;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArgumentParserTest {
	@Test
	@DisplayName("Empty arguments")
	void empty() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ });
		Assertions.assertNull(invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
	}

	@Test
	@DisplayName("Command only")
	void commandOnly() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof" });
		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
	}

	@Test
	@DisplayName("Command and parameter")
	void commandParameter() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--param", "works" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(1, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
		Assertions.assertEquals("works", invocation.getParameter("param"));
	}

	@Test
	@DisplayName("Command and short parameter")
	void commandShortParameter() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "-p", "works" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(1, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
		Assertions.assertEquals("works", invocation.getParameter("p"));
	}

	@Test
	@DisplayName("Command and two parameters")
	void commandTwoParameters() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--param", "works", "--param2", "works2" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(2, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
		Assertions.assertEquals("works", invocation.getParameter("param"));
		Assertions.assertEquals("works2", invocation.getParameter("param2"));
	}

	@Test
	@DisplayName("Command and two short parameters")
	void commandTwoShortParameters() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "-p", "works", "-p2", "works2" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(2, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
		Assertions.assertEquals("works", invocation.getParameter("p"));
		Assertions.assertEquals("works2", invocation.getParameter("p2"));
	}

	@Test
	@DisplayName("Command and one long and one short parameter")
	void commandOneLongOneShortParameter() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--param", "works", "-p2", "works2" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(2, invocation.getParams().size());
		Assertions.assertEquals(0, invocation.getFlags().size());
		Assertions.assertEquals("works", invocation.getParameter("param"));
		Assertions.assertEquals("works2", invocation.getParameter("p2"));
	}

	@Test
	@DisplayName("Command and short flag")
	void commandShortFlag() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "-f" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(1, invocation.getFlags().size());
		Assertions.assertTrue(invocation.getFlag("f"));
	}

	@Test
	@DisplayName("Command and flag with value")
	void commandFlagWithValue() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--flag", "false" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(1, invocation.getFlags().size());
		Assertions.assertFalse(invocation.getFlag("flag"));
	}

	@Test
	@DisplayName("Command and short flag with value")
	void commandShortFlagWithValue() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "-f", "false" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(1, invocation.getFlags().size());
		Assertions.assertFalse(invocation.getFlag("f"));
	}

	@Test
	@DisplayName("Command and multiple flags")
	void commandMultipleFlags() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "-f", "false", "--flag", "true" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(2, invocation.getFlags().size());
		Assertions.assertFalse(invocation.getFlag("f"));
		Assertions.assertTrue(invocation.getFlag("flag"));
	}

	@Test
	@DisplayName("Command and one flag with value and one without")
	void commandOneFlagWithValueAndOneWithout() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "-f", "false", "--flag" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(0, invocation.getParams().size());
		Assertions.assertEquals(2, invocation.getFlags().size());
		Assertions.assertFalse(invocation.getFlag("f"));
		Assertions.assertTrue(invocation.getFlag("flag"));
	}

	@Test
	@DisplayName("Command and one flag and one parameter")
	void commandOneFlagAndOneParameter() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--flag", "--param", "works" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(1, invocation.getParams().size());
		Assertions.assertEquals(1, invocation.getFlags().size());
		Assertions.assertTrue(invocation.getFlag("flag"));
		Assertions.assertEquals("works", invocation.getParameter("param"));
	}

	@Test
	@DisplayName("Command and one parameter and one flag")
	void commandOneParameterAndOneFlag() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--param", "works", "--flag" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(1, invocation.getParams().size());
		Assertions.assertEquals(1, invocation.getFlags().size());
		Assertions.assertTrue(invocation.getFlag("flag"));
		Assertions.assertEquals("works", invocation.getParameter("param"));
	}

	@Test
	@DisplayName("Command and one parameter and one flag with value")
	void commandOneParameterAndOneFlagWithValue() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--param", "works", "--flag", "false" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(1, invocation.getParams().size());
		Assertions.assertEquals(1, invocation.getFlags().size());
		Assertions.assertFalse(invocation.getFlag("flag"));
		Assertions.assertEquals("works", invocation.getParameter("param"));
	}

	@Test
	@DisplayName("Command and one parameter and one flag with value and one flag without value")
	void commandOneParameterAndOneFlagWithValueAndOneFlagWithoutValue() {
		var parser = new ArgumentParser();

		var invocation = parser.parse(new String[]{ "doof", "--param", "works", "--flag", "false", "--flag2" });

		Assertions.assertEquals("doof", invocation.getCommand());
		Assertions.assertEquals(1, invocation.getParams().size());
		Assertions.assertEquals(2, invocation.getFlags().size());
		Assertions.assertFalse(invocation.getFlag("flag"));
		Assertions.assertTrue(invocation.getFlag("flag2"));
		Assertions.assertEquals("works", invocation.getParameter("param"));
	}

}
