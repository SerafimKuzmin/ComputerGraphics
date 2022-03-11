package com.example.demo1.Figure;

import javafx.geometry.Point2D;

public class MyTriangle extends Figure
{
    private Point2D first, second, third;

    public MyTriangle(Point2D first, Point2D second, Point2D third)
    {
        super();
        this.first = first;
        this.second = second;
        this.third = third;
        fill();
    }

    public void fill()
    {
        addPoint(first);
        addPoint(second);
        addPoint(third);
        addPoint(first);
    }
}
