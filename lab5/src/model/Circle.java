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
        int r = (int) radius;

        for (int i = -r; i <= r; i++) {
            for (int j = -2 * r; j <= 2 * r; j++) {
                if (Math.abs(i * i + j * j / 4.0 - r * r) <= r) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Circle [" + super.toString() + ", radius: " + radius +
                ", area: " + calcArea() + "]";
    }


}
