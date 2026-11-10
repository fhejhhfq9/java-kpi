package model;

import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {
    protected String shapeColor;

    public Shape(String shapeColor){
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        return "Color: " + shapeColor;
    }


}
