package fr.uvsq.cprog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    @Test
    public void testAppWithExitCommand() {
        // Set up the input stream with the 'exit' command
        ByteArrayInputStream inputStream = new ByteArrayInputStream("exit\n".getBytes());
        System.setIn(inputStream);

        // Run the main method of the App class
        App.main(new String[]{});

        // Convert the output to a string and check if it contains the expected text
        String outputString = outputStream.toString();
        assertTrue(outputString.contains("Current. Directory: TestDir"));
    }
}
