package fr.uvsq.cprog;
import java.util.*;

public class App 
{
    public static void main(String[] args) {
        // Define the root directory path (replace it with your desired path)
        String rootPath = "TestDir";

        // Create an instance of the console file manager with the root directory path
        ConsoleFileManager fileManager = new ConsoleFileManager(rootPath);

        // Main logic of the application
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            // Display the current directory and prompt the user for a command
            fileManager.curr();
            System.out.print("taper une commande ou taper 'exit' pour quitter): \n");
            command = scanner.nextLine();

            // Process the user command
            if (!command.equalsIgnoreCase("exit")) {
                fileManager.processCommand(command);
            }

        } while (!command.equalsIgnoreCase("exit"));

        // Close the scanner
        scanner.close();
    }
}    
//mvn clean jacoco:prepare-agent install jacoco:report
