package com.example.demo1.Figure;

import javafx.geometry.Point2D;

public class MyEllipseUpperArc extends Figure
{
    private Point2D position;
    private double width;
    private double heigth;
    public MyEllipseUpperArc(double cx, double cy, double w, double h)
    {
        super();
        this.position = new Point2D(cx, cy);
        this.width = w;
        this.heigth = h;
        fill();
    }

    public MyEllipseUpperArc(Point2D position, double w, double h)
    {
        super();
        this.position = position;
        this.width = w;
        this.heigth = h;
        fill();
    }

    private void fill()
    {
        double a = width / 2;
        double b = heigth / 2;
        double xc = position.getX() + a;
        double yc = position.getY() + b;
        for (double x = xc - a; x <= xc + a; x++)
        {
            double y = yc - (Math.sqrt(b * b * (1 - Math.pow(x - xc, 2) / (a * a))) - b) - b;
            addPoint(x, y);
        }
    }
}
