package view;

import model.*;

public class ShapeView {
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

    public void displayTitle(String title) {
        System.out.println("\n=== " + title + " ===");
    }
}
