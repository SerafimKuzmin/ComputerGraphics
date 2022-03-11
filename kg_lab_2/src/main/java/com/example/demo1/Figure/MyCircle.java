package com.example.demo1.Figure;

import javafx.geometry.Point2D;

public class MyCircle extends Figure
{
    private Point2D center;
    private double r;
    public MyCircle(double cx, double cy, double r)
    {
        super();
        this.center = new Point2D(cx, cy);
        this.r = r;
        fill();
    }

    public MyCircle(Point2D center, double r)
    {
        super();
        this.center = center;
        this.r = r;
        fill();
    }

    private void fill()
    {
        double cx = center.getX(), cy = center.getY();
        for (double x = cx - r; x <= cx + r; x++)
        {
            double y = Math.sqrt((r * r) - ((x - cx) * (x - cx))) + cy;
            addPoint(x, y);
        }

        for (double x = cx + r; x >= cx - r; x--)
        {
            double y = -Math.sqrt((r * r) - ((x - cx) * (x - cx))) + cy;
            addPoint(x, y);
        }
    }


}
