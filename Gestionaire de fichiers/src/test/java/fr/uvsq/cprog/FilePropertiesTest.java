
package fr.uvsq.cprog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FilePropertiesTest {

    @Test
    void testFilePropertiesConstructorAndGetters() {
        // Arrange
        int ner = 1;
        String name = "TestDir";
        String type = "Directory";
        String path = "/TestDir";

        // Act
        FileProperties file = new FileProperties(ner, name, type, path);

        // Assert
        assertEquals(ner, file.getNER());
        assertEquals(name, file.getName());
        assertEquals(type, file.getType());
        assertEquals(path, file.getPath());
    }

    @Test
    void testSetPath() {
        // Arrange
        FileProperties file = new FileProperties(1, "TestDir", "Directory", "/TestDir");

        // Act
        String newPath = "/new/path/to/testFile";
        file.setPath(newPath);

        // Assert
        assertEquals(newPath, file.getPath());
    }

    
}
