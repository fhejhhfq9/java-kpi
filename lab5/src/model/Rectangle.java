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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Rectangle [" + super.toString() + ", width: " + width +
                ", height: " + height + ", area: " + calcArea() + "]";
    }


}
