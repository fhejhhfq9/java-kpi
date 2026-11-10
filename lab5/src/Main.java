import controller.ShapeController;
import model.*;
import view.ShapeView;
import task2.ShapeFileManager;

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

        new ShapeController(shapes, new ShapeView(), new ShapeFileManager()).run();
    }
}