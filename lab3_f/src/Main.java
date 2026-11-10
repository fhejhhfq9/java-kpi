import model.*;
import controller.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Rectangle("Red", 5, 4),
                new Circle("Blue", 3),
                new Triangle("Green", 6, 4),
                new Rectangle("Yellow", 7, 3),
                new Circle("Red", 2),
                new Triangle("Blue", 8, 5),
                new Rectangle("Green", 4, 6),
                new Circle("Yellow", 5),
                new Triangle("Red", 10, 3),
                new Rectangle("Blue", 2, 8),
                new Circle("Green", 4),
                new Triangle("Yellow", 7, 6)
        };

        ShapeController controller = new ShapeController(shapes);
        ShapeView view = new ShapeView();

        view.drawShapes(shapes);

        double totalArea = controller.calculateTotalArea();
        view.displayTotalArea(totalArea);

        System.out.println("total area by type");
        double totalAreaRect = controller.calculateAreaByType(Rectangle.class);
        view.displayAreaByType("Rectangle", totalAreaRect);
        double totalAreaTriangle = controller.calculateAreaByType(Triangle.class);
        view.displayAreaByType("Triangle", totalAreaTriangle);
        double totalAreaCircle = controller.calculateAreaByType(Circle.class);
        view.displayAreaByType("Circle", totalAreaCircle);

        System.out.println("Sorted by area: ");
        controller.sortByArea();
        view.displayShapes(controller.getShapes());

        System.out.println("Sorted by color:");
        controller.sortByColor();
        view.displayShapes(controller.getShapes());

    }
}
