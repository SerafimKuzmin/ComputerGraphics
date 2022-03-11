package com.example.demo1.Figure;

import javafx.geometry.Point2D;

public class MyLine extends Figure
{
    private Point2D first, second;

    public MyLine(Point2D first, Point2D second)
    {
        super();
        this.first = first;
        this.second = second;
        fill();
    }

    private void fill()
    {
        addPoint(first);
        addPoint(second);
    }
}
