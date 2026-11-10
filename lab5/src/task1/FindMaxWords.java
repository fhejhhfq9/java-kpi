package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FindMaxWords {

    static int countWords(String line){
        String trim = line.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length;
    }

    static String findLongestLine(String path) throws IOException {
        String longest = null;
        int max = -1;
        try (BufferedReader br = new BufferedReader(
                new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                int words = countWords(line);
                if (words > max) {
                    max = words;
                    longest = line;
                }
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        String path = scanner.nextLine().trim();
        try {
            String line = findLongestLine(path);
            if (line == null) {
                System.out.println("Файл порожній.");
            } else {
                System.out.println("Рядок з найбільшою кількістю слів (" + countWords(line) + "):");
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }




}
