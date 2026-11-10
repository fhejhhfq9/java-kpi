package model;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(String shapeColor,double w, double h){
        super(shapeColor);
        this.width = w;
        this.height = h;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing rectangle");

    }

    @Override
    public String toString() {
        return "Rectangle [" + super.toString() + ", width: " + width +
                ", height: " + height + ", area: " + calcArea() + "]";
    }


}
