package fr.uvsq.cprog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConsoleFileManagerInitTest {
    //private ConsoleFileManager fileManager;
    private Directory currentDirectory; // Move the declaration here

    @BeforeEach
    public void setUp() {
        //fileManager = new ConsoleFileManager("TestDir");
        currentDirectory = new Directory(0, "TestDir", "TestDir"); // Assign the value here
    }

    @Test
    public void testFileManagerInitialization() {
        
        Assertions.assertEquals("TestDir", currentDirectory.getPath());
    }

    @Test
    public void testFileManagerDefaultState() {
        // Add tests to check the default state of the file manager
        Assertions.assertNotNull(currentDirectory.getElements());
        Assertions.assertTrue(currentDirectory.getElements().isEmpty());
    }
}
