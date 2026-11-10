package controller;

import model.*;
import java.util.Comparator;

public class ShapeController {
    private Shape[] shapes;

    public ShapeController(Shape[] shapes) {
        this.shapes = shapes;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    public double calculateTotalArea() {
        double total = 0;
        for (Shape shape : shapes) {
            total += shape.calcArea();
        }
        return total;
    }

    public double calculateAreaByType(Class<?> type) {
        double total = 0;
        for (Shape shape : shapes) {
            if (type.isInstance(shape)) {
                total += shape.calcArea();
            }
        }

        return total;
    }

    public void sortByArea() {
        java.util.Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
    }

    public void sortByColor() {
        java.util.Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareTo(s2.getShapeColor());
            }
        });
    }



}
