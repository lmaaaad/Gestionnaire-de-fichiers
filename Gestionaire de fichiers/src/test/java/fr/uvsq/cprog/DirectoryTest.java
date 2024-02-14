package fr.uvsq.cprog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DirectoryTest {

    @Test
    void testDirectoryConstructorAndGetters() {
        // Arrange
        int ner = 1;
        String name = "TestDir";
        String path = "/TestDir";

        // Act
        Directory directory = new Directory(ner, name, path);

        // Assert
        assertEquals(ner, directory.getNER());
        assertEquals(name, directory.getName());
        assertEquals("directory", directory.getType()); 
        assertEquals(path, directory.getPath());
        assertNotNull(directory.getElements());
        assertTrue(directory.getElements().isEmpty());
    }

    @Test
    void testAddElementAndGetElements() {
        // Arrange
        Directory directory = new Directory(1, "testDir", "/TestDir");
        FileProperties file = new FileProperties(2, "notes1.txt", "file", "/TestDir/notes1.txt");

        // Act
        directory.addElement(file);

        // Assert
        assertEquals(1, directory.getElements().size());
        assertTrue(directory.getElements().contains(file));
    }

}
