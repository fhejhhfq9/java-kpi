package model;

public class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(String shapeColor,double b, double h){
        super(shapeColor);
        this.base = b;
        this.height = h;
    }

    @Override
    public double calcArea() {
        return base * height / 2;
    }

    @Override
    public void draw() {
        System.out.println("Drawing triangle");

    }


    @Override
    public String toString() {
        return "Triangle [" + super.toString() +  ", base: " + base +
                ", height: " + height + ", area: " + calcArea() + "]";
    }
}

