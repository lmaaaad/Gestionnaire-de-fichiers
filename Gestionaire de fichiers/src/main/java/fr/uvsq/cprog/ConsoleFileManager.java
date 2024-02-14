package fr.uvsq.cprog;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ConsoleFileManager 
{
    private Directory currentDirectory;
    private int finalNER;
    public String Output = "" ;


    private String copiedFilePath;
   
   public ConsoleFileManager(String rootPath) {
    this.currentDirectory = new Directory(0, "TestDir", rootPath);
    finalNER = 0;
    }
/*================================================================================================ */    

    public void processCommand(String command) {
        
    String[] parts = command.split(" ");
    
    if (parts.length == 0) {
        System.out.println("Incomplete command.");
        return;
    }
    int NER =0;
    String cmd; //= parts[1].toLowerCase();
    if (isInteger(parts[0])){
        cmd = parts[1].toLowerCase();
         NER = (parts.length > 1 && parts[0].matches("\\d+")) ? Integer.parseInt(parts[0]) : finalNER;
    }else  {cmd = parts[0].toLowerCase();
            NER = (parts.length > 1 && parts[1].matches("\\d+")) ? Integer.parseInt(parts[1]) : finalNER;}

    switch (cmd) {
        case "copy":
            copy(NER);
            break;
        case "cut":
            cut(NER);
            break;
        case "paste":
            pasteFile();
            break;
        case "..":
            GoUp();
            break;
        case ".":
            GoTo(NER);
            break;
        case "mkdir":
            if (parts.length > 1) {
                createDirectory(parts[1]);
            } else {
                System.out.println("Missing directory name.");
            }
            break;
        case "visu":
            viewFile(NER);
            break;
        case "find":
            if (parts.length > 1) {
                findFile(parts[1]);
            } else {
                System.out.println("Missing file name.");
            }
            break;
        case "+":
            Appendtxt(NER, parts);
            break;
        case "-":
            DeleteAnnot(NER);
            break;
        case "help":
            help();
            break;    
        default:
            System.out.println("Unrecognized command.");
            break;

    }
    }
/*================================================================================================ */    
                             /*Pour Extraire le NER des fichiers  */
    private String getPathByNER(int NER) {
        File[] files = new File(currentDirectory.getPath()).listFiles();

        if (files != null && NER >= 1 && NER <= files.length) {
            int ner = 1;
            for (File file : files) {
                if (ner == NER) {
                    return file.getAbsolutePath();
                }
                ner++;
            }
        }
        return null;
    }
/*================================================================================================ */    


    /*public void copy(int ner) {
         String sourceFilePath = getPathByNER(ner);
         System.out.println(ner);
        if (sourceFilePath != null) {
            File sourceFile = new File(sourceFilePath);
            if (sourceFile.isFile()) {
                String fileName = sourceFile.getName().replaceFirst("[.][^.]+$", "");
                String fileExtension = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
                String newFileName = fileName + "-copy" + fileExtension;
                String targetDirectoryPath = sourceFile.getParent();


                Path targetFilePath = Paths.get(targetDirectoryPath, newFileName);

                try {
                    Files.copy(sourceFile.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    Output = "File copied successfully. New file: " + newFileName;
                    System.out.println(Output);
                } catch (Exception e) {
                    e.printStackTrace();
                    Output = "Error copying file.";
                    System.out.println(Output);
                }
                
            }
        } else {
            Output = "File not found for NER " + ner;
            System.out.println(Output);
        }
    } */
/*================================================================================================ */    

    public void copy(int NER) {
        String sourceFilePath = getPathByNER(NER);
        if (sourceFilePath != null) {
            File sourceFile = new File(sourceFilePath);
            if (sourceFile.isFile()) {
                String fileName = sourceFile.getName().replaceFirst("[.][^.]+$", "");
                String fileExtension = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
                String newFileName = fileName + "-copy" + fileExtension;
                copiedFilePath = sourceFile.getAbsolutePath();

                Output = "File copied successfully. New file: " + newFileName ;
                System.out.println(Output);
            } else {
                Output = "The element corresponding to NER is not a file.";
                System.out.println(Output);
            }
        } else {
            Output = "File not found for NER " + NER;
            System.out.println(Output);
        }
    }
                                /*=========================================== */    

    public void pasteFile() {
        if (copiedFilePath != null) {
            String sourceFilePath = copiedFilePath;

            if (sourceFilePath != null) {
                File sourceFile = new File(sourceFilePath);
                if (sourceFile.isFile()) {
                    String fileName = sourceFile.getName().replaceFirst("[.][^.]+$", "");
                    String fileExtension = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
                    String newFileName = fileName + "-copy" + fileExtension;
                    String currentPath = currentDirectory.getPath();

                    String newFilePath = Paths.get(currentPath, newFileName).toString();

                    try (InputStream inStream = new FileInputStream(sourceFile);
                         OutputStream outStream = new FileOutputStream(newFilePath)) {

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inStream.read(buffer)) > 0) {
                            outStream.write(buffer, 0, length);
                        }

                        Output = "File pasted successfully." ;
                        System.out.println(Output);
                    } catch (IOException e) {
                        Output = "Error pasting file: " + e.getMessage() ;
                        System.out.println(Output);
                    }
                } else {
                    Output = "The copied element is not a file.";
                    System.out.println(Output);
                }
            } else {
                Output = "Copied file not found.";
                System.out.println(Output);
            }
        } else {
            Output = "No file has been copied previously.";
            System.out.println(Output);
        }
    }

/*================================================================================================ */    

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
/*================================================================================================ */    

    
    public void cut(int ner) {
         String path = getPathByNER(ner);
        if (path != null) {
            File fileOrDirectory = new File(path);

            if (fileOrDirectory.exists()) {
                try {
                    if (fileOrDirectory.isFile()) {
                        Files.deleteIfExists(Paths.get(path));
                        Output = "File deletion successful.";
                        System.out.println(Output);
                    } else if (fileOrDirectory.isDirectory()) {
                        FileUtils.deleteDirectory(fileOrDirectory); //FileUtils not working 
                        Output = "Directory deletion successful.";
                        System.out.println(Output);
                    }
                } catch (IOException e) {
                    Output = "Error deleting: " + e.getMessage();
                    System.out.println(Output);
                }
            } else {
                Output = "File or directory not found for NER " + ner;
                System.out.println(Output);
            }
        } else {
            Output = "File or directory not found for NER " + ner;
            System.out.println(Output);
        }
    }
/*================================================================================================ */    


    public void GoUp() { 
        //il faut pas sortir du TestDir
        if (currentDirectory.getPath().equals("TestDir")) {
            Output = "You are already at the root directory.";
            System.out.println(Output);
            return;
        }

        String parentPath = currentDirectory.getPath();
        File parentFile = new File(parentPath).getParentFile();

        if (parentFile != null &&  !parentPath.endsWith("TestDir") ) {
            currentDirectory = new Directory(0, parentFile.getName(), parentFile.getAbsolutePath());
        } else {
            Output = "You are already at the root directory.";
            System.out.println(Output);
        }
    }
/*================================================================================================ */    


    public void GoTo(int ner){
        String targetDirectoryPath = getPathByNER(ner);
        System.out.println("\n\n\n\n\n"+ targetDirectoryPath +"\n\n\n\n " + ner);

        if (targetDirectoryPath != null) {
            File targetDirectory = new File(targetDirectoryPath);
            if (targetDirectory.isDirectory()) {
                currentDirectory.setPath(targetDirectoryPath);
            } else {
                Output = "Ce NER est un fichier , PAS un repertoire !! ";
                System.out.println(Output);
            }
        } else {
            Output = "Directory not found.";
            System.out.println(Output);
        }
        return;
    }

 /*================================================================================================ */    
   
    public List<String> curr() {
         System.out.println("Current. Directory: " + currentDirectory.getPath());

        List<String> contentList = new ArrayList<>();
        File[] files = new File(currentDirectory.getPath()).listFiles();
        
        if (files != null) {
            int ner = 1;
            System.out.printf("%-" + 5 + "s \t %-" + 30 + "s \t TYPE%n","NER","NOM");
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.printf("%-" + 5 + "s \t %-" + 30 + "s \t (directory)%n", ner, file.getName());
                } else {
                    System.out.printf("%-" + 5 + "s \t %-" + 30 + "s \t (file)%n", ner, file.getName());
                }
                ner++;
            }
          
        } else {
            System.out.println("Error retrieving directory items.");
        }

        return contentList;
    }
/*================================================================================================ */    

    public void createDirectory(String name) {
        //  Create a directory
        int newNER = currentDirectory.getElements().size() + 1;
        Directory newDirectory = new Directory(newNER, name, currentDirectory.getPath());
        Path newDirectoryPath = Paths.get(currentDirectory.getPath(), name);

        try {
            Files.createDirectory(newDirectoryPath);
            newDirectory.setPath(newDirectoryPath.toString());
            currentDirectory.addElement(newDirectory);

            // Crée le fichier notes.txt dans le nouveau répertoire
            Path notesFilePath = Paths.get(newDirectoryPath.toString(), "notes.txt");
            Files.createFile(notesFilePath);

            Output = "The Directory " + newDirectory.getName()+" created successfully created.";
            System.out.println(Output);
        } catch (IOException e) {
            Output = "Error creating directory.";
            System.out.println(Output);
        }
    }
/*================================================================================================ */    

    public void viewFile(int ner) {
       
     String filePath = getPathByNER(ner);

    if (filePath != null) {
        File targetFile = new File(filePath);

        if (targetFile.isFile()) {
            String fileName = targetFile.getName();

            if (fileName.endsWith(".txt") || fileName.endsWith(".text")) {
                try {
                    System.out.printf("\nLe fichier sélectionné c'est : %s%n\n", targetFile.getName());

                    InputStream ips = new FileInputStream(targetFile);
                    InputStreamReader ipsr = new InputStreamReader(ips);
                    BufferedReader br = new BufferedReader(ipsr);
                    String ligne;
                    while ((ligne = br.readLine()) != null) {
                        System.out.println(ligne);
                    }
                    br.close();
                } catch (IOException e) {
                    Output = "Error reading file: " + e.getMessage();
                    System.out.println(Output);
                }
                if (Output.isEmpty()) {
                    Output = "Empty file";
                }
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                try {
                    System.out.printf("\nL'image' sélectionné c'est : %s%n\n", targetFile.getName());

                    BufferedImage image = ImageIO.read(targetFile);
                    displayImage(image);
                    // Display or process the image as needed
                } catch (IOException e) {
                    Output = "Error reading image file: " + e.getMessage();
                    System.out.println(Output);
                }
            } else {
                Output = "\nLe fichier n'est pas de type texte ni d'image. Affichage de la taille : " + targetFile.length() + " bytes\n";
                System.out.println(Output);
            }
        } else {
            Output = "The element corresponding to NER is not a file.";
            System.out.println(Output);
        }
    } else {
        Output = "\nFile not found for NER " + ner + "\n";
        System.out.println(Output);
    }
}

/*================================================================================================ */    
    
public void Appendtxt(int ner, String[] parts) {
    String directoryPath = currentDirectory.getPath();
    String filePath = getPathByNER(ner);
   if (filePath != null) {
    if (directoryPath != null) {
        Path notesFilePath = Paths.get(directoryPath, "notes.txt");

        if (Files.exists(notesFilePath)) {
            String annotationText = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));

            try {
                List<String> lines = Files.readAllLines(notesFilePath, StandardCharsets.UTF_8);
            
                if (ner >= 1) {
                    // Add empty lines if needed
                    while (ner > lines.size()) {
                        lines.add("");
                    }
            
                    String existingLine = lines.get(ner - 1);
                    String modifiedLine = existingLine + " " + annotationText;
            
                    // Replace the existing line with the modified line
                    lines.set(ner - 1, modifiedLine);
            
                    // Write the modified lines back to the file
                    Files.write(notesFilePath, lines, StandardCharsets.UTF_8);
                    
                    Output = "Annotation added successfully to NER " + ner;
                    System.out.println(Output);
                } else {//this test is not needed because getpath returns null if the file is not found
                    Output = "Invalid NER. The NER should be greater than or equal to 1";
                    System.out.println(Output);
                }
            } catch (IOException e) {
                Output = "Error adding annotation: " + e.getMessage();
                System.out.println(Output);
            }
            }

            }
    
            }else {
            Output = "File not found for NER " + ner;
            System.out.println(Output);
            }
}
/*================================================================================================ */    

    public void findFile (String nomfichier) 
        {
            Output = "" ;
            findFileRecursive(currentDirectory.getPath(), nomfichier);
            if (Output == null || Output.isEmpty()) {
                Output = "File not found: " + nomfichier;
                System.out.println(Output);
            }
        }
         void findFileRecursive(String directoryPath, String nomfichier) {
            File directory = new File(directoryPath);
    
            File[] files = directory.listFiles();
    
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals(nomfichier)) {
                        Output = file.getAbsolutePath();
                        System.out.println("File found at" + Output);
                        return ; // Ajout pour arrêter la recherche une fois le fichier trouvé
                    }
                    if (file.isDirectory()) {
                        findFileRecursive(file.getAbsolutePath(), nomfichier);
                    }
                }
            }
        }
    
/*================================================================================================ */    

    public void DeleteAnnot(int ner) {
            String directoryPath = currentDirectory.getPath();
            String filePath = getPathByNER(ner);
          if (filePath != null) {
            if (directoryPath != null) {
                Path notesFilePath = Paths.get(directoryPath, "notes.txt");
        
                if (Files.exists(notesFilePath)) {
                    try {
                        List<String> lines = Files.readAllLines(notesFilePath, StandardCharsets.UTF_8);
                            // Remove the line corresponding to the ner value
                            System.out.println(notesFilePath);
                            lines.remove(ner -1 );
                            Files.write(notesFilePath, lines, StandardCharsets.UTF_8);
                            Output = "Annotation deleted successfully for NER " + ner;
                            System.out.println(Output);
                    
                    } catch (IOException e) {
                        Output = "Error deleting annotation: " + e.getMessage();
                        System.out.println(Output);
                    }
                }
            } else {
                Output = "Directory path is null.";
                System.out.println(Output);
            }
        }else {
            Output = "File not found for NER " + ner;
            System.out.println(Output);
            }


    }
/*================================================================================================ */    


public void displayImage(BufferedImage image) {
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(image));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.setSize(image.getWidth(), image.getHeight());
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
}
/*================================================================================================ */    


    public void help() {
       //  Display the help menu
        AnsiConsole.systemInstall(); // Install Jansi to enable ANSI escape codes
        System.out.println("\n");
        System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a("Command").a(" ".repeat(10)).fg(Ansi.Color.WHITE).a("      |").a(" ".repeat(10)).fg(Ansi.Color.BLUE).a("Description").reset());

        System.out.println("-".repeat(60)); // Line separator


        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("[<NER>] copy").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("past ; si l’élément existe, alors le nom du nouvel élément sera concaténé avec \"-copy\"").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("[<NER>] cut").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("").reset()); // Empty description

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("..").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("pour remonter d’un cran dans le système de fichiers").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("[<NER>] .").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("pour entrer dans un répo à condition que le NER désigne un répertoire. Exemple \"4 .\"").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("mkdir <nom>").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("pour créer un répertoire").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("[<NER>] visu").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("permet de voir le contenu d’un fichier txt. ou bien la taille d'un repo").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("find <nom fichier>").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("Recherche un fichier dans toutes les sous-répertoires du répertoire courant.").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("3 + \"Texte ici\"").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("le texte est ajouté ou concaténé au texte existant sur l’ER").reset());

        System.out.printf("%-30s %s %-29s%n",
            Ansi.ansi().fg(Ansi.Color.YELLOW).a("3 -").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("|").reset(),
            Ansi.ansi().fg(Ansi.Color.WHITE).a("retire tout le texte associé à l’ER 3 \n ").reset());


        // Uninstall Jansi to disable ANSI escape codes
      
 AnsiConsole.systemUninstall();
 
}
     
 }


