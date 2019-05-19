package com.wggy.prune.info1113;//JUnit
// javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar:hamcrest-2.1.jar Atomination.java AtominationTest.java
// java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar:hamcrest-2.1.jar org.junit.runner.JUnitCore AtominationTest
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.Matchers.containsString;

public class AtominationTest{
	
	@Test
	public void testStart(){
		String[] s = new String[]{"2", "5", "5"};
		String string = "Game Ready\n" + "Red's Turn\n";
		assertEquals(string, Atomination.start(s));
	}
	
	@Test
	public void testPlace(){
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String[] s = new String[]{"2", "5", "5"};
		Atomination.start(s);
		Atomination.place(1, 1, true);
		assertThat(outContent.toString(), containsString("Invalid Coordinates"));
	}
	
}
