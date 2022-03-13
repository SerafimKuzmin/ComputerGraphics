package com.example.demo1.Figure;

import javafx.geometry.Point2D;

public class MyRectangle extends Figure
{
    private Point2D position;
    private double width;
    private double heigth;

    public MyRectangle(Point2D pos, double width, double heigth)
    {
        super();
        this.position = pos;
        this.width = width;
        this.heigth = heigth;
        fill();
    }

    public void fill()
    {
        addPoint(position);
        addPoint(new Point2D(position.getX() + width, position.getY()));
        addPoint(new Point2D(position.getX() + width, position.getY() + heigth));
        addPoint(new Point2D(position.getX(), position.getY() + heigth));
        addPoint(position);
    }
}

