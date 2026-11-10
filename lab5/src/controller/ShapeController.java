package controller;

import model.*;
import view.ShapeView;
import task2.ShapeFileManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ShapeController {
    private Shape[] shapes;
    private final ShapeView view;
    private final ShapeFileManager fileManager;

    public ShapeController(Shape[] shapes, ShapeView view, ShapeFileManager fileManager) {
        this.shapes = shapes;
        this.view = view;
        this.fileManager = fileManager;
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.displayMenu();
            switch (view.readMenuChoice()) {
                case 1 -> view.displayShapes(shapes);
                case 2 -> view.drawShapes(shapes);
                case 3 -> view.displayTotalArea(calculateTotalArea());
                case 4 -> areaByType();
                case 5 -> {
                    sortByArea();
                    view.displayShapes(shapes);
                }
                case 6 -> {
                    sortByColor();
                    view.displayShapes(shapes);
                }
                case 7 -> saveToFile();
                case 8 -> loadFromFile();
                case 0 -> running = false;
                default -> view.displayMessage("Невідомий пункт меню.");
            }
        }

    }

    private double calculateTotalArea() {
        double total = 0;
        for (Shape shape : shapes) {
            total += shape.calcArea();
        }
        return total;
    }

    private double calculateAreaByType(Class<? extends Shape> type) {
        double total = 0;
        for (Shape shape : shapes) {
            if (type.isInstance(shape)) {
                total += shape.calcArea();
            }
        }
        return total;
    }

    private void sortByArea() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
    }

    private void sortByColor() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareTo(s2.getShapeColor());
            }
        });
    }


    private void areaByType() {
        String name = view.readString("Вид фігури (Rectangle / Triangle / Circle): ");
        Class<? extends Shape> type;
        switch (name.toLowerCase()) {
            case "rectangle" -> type = Rectangle.class;
            case "triangle" -> type = Triangle.class;
            case "circle" -> type = Circle.class;
            default -> {
                view.displayMessage("Помилка: невідомий вид фігури ");
                return;
            }
        }
        view.displayAreaByType(name, calculateAreaByType(type));
    }

    private void saveToFile() {
        String path = view.readString("Введіть шлях  файлу для збереження: ");
        if (path.isEmpty()) {
            view.displayMessage("Помилка: шлях до файлу не введено.");
            return;
        }
        try {
            fileManager.save(shapes, path);
            view.displayMessage("Набір збережено у файл: " + path);
        } catch (IOException e) {
            view.displayMessage("Помилка запису у файл: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        String path = view.readString("Введіть шлях файлу для читання: ");
        if (path.isEmpty()) {
            view.displayMessage("Помилка: шлях до файлу не введено.");
            return;
        }
        try {
            shapes = fileManager.load(path);
            view.displayMessage("Набір прочитано з файлу. Кількість фігур: " + shapes.length);
        } catch (IOException e) {
            view.displayMessage("Помилка читання файлу: " + e.getMessage());
        }
    }
}