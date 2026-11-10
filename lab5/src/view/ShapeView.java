package view;

import model.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ShapeView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1 - Показати фігури");
        System.out.println("2 - Намалювати фігури");
        System.out.println("3 - Сумарна площа всіх фігур");
        System.out.println("4 - Сумарна площа фігур заданого виду");
        System.out.println("5 - Сортувати за площею");
        System.out.println("6 - Сортувати за кольором");
        System.out.println("7 - Зберегти набір у файл");
        System.out.println("8 - Прочитати набір з файлу");
        System.out.println("0 - Вихід");
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readMenuChoice() {
        while (true) {
            try {
                return Integer.parseInt(readString("Ваш вибір: "));
            } catch (NumberFormatException e) {
                displayMessage("Помилка: введіть ціле число від 0 до 8.");
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayShapes(Shape[] shapes) {
        for (Shape shape : shapes) {
            System.out.println(shape);
        }
    }

    public void drawShapes(Shape[] shapes) {
        for (Shape shape : shapes) {
            System.out.println(shape);
            shape.draw();
            System.out.println();
        }
    }

    public void displayTotalArea(double area) {
        System.out.printf("Total area: %.2f%n", area);
    }

    public void displayAreaByType(String type, double area) {
        System.out.printf("Total area of %s: %.2f%n", type, area);
    }
}
