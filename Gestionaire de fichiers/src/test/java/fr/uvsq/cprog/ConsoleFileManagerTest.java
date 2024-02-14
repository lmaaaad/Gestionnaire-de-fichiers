package fr.uvsq.cprog;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.fusesource.jansi.AnsiConsole;
import java.util.UUID;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConsoleFileManagerTest 
{   
    private ConsoleFileManager fileManager, fileManager2;
    //private Directory testDirectory;
    

    @BeforeEach
    public void setUp() {
        //testDirectory = new Directory(0, "TestDir", "TestDir");
        fileManager = new ConsoleFileManager("TestDir");
        fileManager2 = new ConsoleFileManager("TestDir\new");     
    }
    @Test
    public void testProcessCommand() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act test de zero command
        fileManager.processCommand(" ");
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Incomplete command."));
        // Reset System.out
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act test d'une commande inconnue
        fileManager.processCommand("aymen");
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Unrecognized command."));
        // Reset System.out
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act test d'une commande incomplète
        fileManager.processCommand("mkdir");
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Missing directory name."));
    }
  
    @Test
    void testDisplayHelp() {
      // Arrange
       AnsiConsole.systemInstall();
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      System.setOut(new PrintStream(output));

      // Act
      fileManager.help();

      // Assert
      String outputStr = output.toString();
      System.out.println(outputStr);
      assertThat(outputStr).contains("\u001B");
      assertTrue(outputStr.contains("retire tout le texte associé à l’ER 3"));
      AnsiConsole.systemUninstall();
    }   
    @Test
    public void testViewFile() {
           
             // Arrange
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
    
            // Act and Assert for NER = 1
            fileManager.viewFile(1);
            String outputStr = output.toString();
            System.out.println(outputStr);
            assertTrue(outputStr.contains("Le fichier sélectionné c'est"));
            
            // Reset System.out
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            System.setOut(new PrintStream(output));
            // Act and Assert for NER = 25 (non-existent file)
            fileManager.viewFile(25);
            outputStr = output.toString();
            System.out.println(outputStr);
           assertTrue(outputStr.contains("File not found for"));
            // Reset System.out
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            System.setOut(new PrintStream(output));
            // Act and Assert for NER = 1 (image file)
            fileManager2.viewFile(1);
            outputStr = output.toString();
            System.out.println(outputStr);
           assertTrue(outputStr.contains("L'image' sélectionné c'est"));
    }
    @Test
    void testDisplayImage() throws IOException {
        // Arrange
        String imagePath = "TestDir\\1Capture d'écran 2023-12-16 110722.png";
        File targetimagFile = new File(imagePath);
        BufferedImage image = ImageIO.read(targetimagFile);


        // Act
        fileManager.displayImage(image);
    }      
    @Test
    public void testFindFile() {
        // Arrange
        String nomFichier = "notes.txt"; // file name we want to find
         ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        
        // Act and Assert for kiko.txt
            fileManager.findFile(nomFichier);
            String outputStr = output.toString();
            System.out.println(outputStr);
            assertTrue(outputStr.contains("File found at"));
        // Reset System.out
        nomFichier = "DoesNotExist.txt"; // file name we want to find
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        // Act and Assert for DoesNotExist.txt
            fileManager.findFile(nomFichier);
            outputStr = output.toString();
            System.out.println(outputStr);
            assertFalse(outputStr.contains("File found at"));
        // Reset System.out
        System.setOut(System.out);
    }
    
     @Test
    void testGoUp() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.GoUp();
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("You are already at the root directory."));
    }
     @Test
    void testGoTo() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.GoTo(20);
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Directory not found."));
    }
     @Test
    void testAppendText() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        String[] parts = {" "," ","aymen","imad"};;
        //Act
        fileManager.Appendtxt(2, parts);
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Annotation added successfully to NER"));

        // Reset System.out
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act test d'une commande incomplète
        fileManager.Appendtxt(40, parts);
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("File not found for NER"));
    }
    @Test
    void testDeleteAnnot() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.DeleteAnnot(5);
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Annotation deleted successfully for NER"));

        // Reset System.out
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act test d'une commande incomplète
        fileManager.DeleteAnnot(40);
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("File not found for NER"));
    }       
    @Test 
    public void testCopy() {
        // Arrange copy a file then paste it
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
       //Act
        fileManager.copy(1);
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("File copied successfully."));
        // Arrange
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.pasteFile();
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertFalse(outputStr.contains("File Pasted successfully."));

        //Arange invalid NER then try to paste
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.copy(30);
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("File not found for NER"));
         // Arrange
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.pasteFile();
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertFalse(outputStr.contains("No file has been copied previously."));
    }
   /* @Test
    public void testPaste() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act
        fileManager.pasteFile();
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertFalse(outputStr.contains("File Pasted successfully."));

        // Reset System.out
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        //Act test d'une commande incomplète
        fileManager.processCommand("mkdir");
        // Assert
        outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Missing directory name."));
    } */
   /* @Test
     public void testCut() {
               // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
       //Act
        fileManager.cut(20);;
        // Assert
        String outputStr = output.toString();
        assertFalse(outputStr.contains("deletion successful."));
    }
   */ @Test
    void testCreateDirectory() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
       //Act
       String randomDirectoryName = generateRandomDirectoryName();

        fileManager.createDirectory(randomDirectoryName);
        // Assert
        String outputStr = output.toString();
        System.out.println(outputStr);
        assertTrue(outputStr.contains("successfully"));
    }
    private String generateRandomDirectoryName() {
        return "Directory_" + UUID.randomUUID().toString();
    }

}

  



