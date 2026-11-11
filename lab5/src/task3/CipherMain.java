package task3;

import java.io.IOException;
import java.util.Scanner;

public class CipherMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CipherService service = new CipherService();

        System.out.print("Mode (1 - encrypt, 2 - decrypt): ");
        String mode = scanner.nextLine().trim();
        System.out.print("Input file: ");
        String inPath = scanner.nextLine().trim();
        System.out.print("Output file: ");
        String outPath = scanner.nextLine().trim();
        System.out.print("Key character: ");
        String keyLine = scanner.nextLine();

        if (keyLine.isEmpty()) {
            System.out.println("Error: key character not entered.");
            return;
        }
        char key = keyLine.charAt(0);

        try {
            switch (mode) {
                case "1" -> service.encrypt(inPath, outPath, key);
                case "2" -> service.decrypt(inPath, outPath, key);
                default -> {
                    System.out.println("Error: unknown mode.");
                    return;
                }
            }
            System.out.println("Done: " + outPath);
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}