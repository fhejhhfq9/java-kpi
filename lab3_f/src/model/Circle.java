package model;

public class Circle extends Shape {
    private final double radius;

    public Circle(String shapeColor, double r) {
        super(shapeColor);
        this.radius = r;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }

    @Override
    public String toString() {
        return "Circle [" + super.toString() + ", radius: " + radius +
                ", area: " + calcArea() + "]";
    }


}
