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
        var r = radius(0, a, b);
        for (double angle = Math.PI; angle <= Math.PI * 2; angle += 1 / r)
        {
            r = radius(angle, a, b);
            double x = xc + a * Math.cos(angle);
            double y = yc + b * Math.sin(angle);
            addPoint(x, y);
        }
        addPoint(xc + a, yc);
    }
    private double dx(double t, double a)
    {
        return -a * Math.sin(t);
    }

    private double dy(double t, double b)
    {
        return b * Math.cos(t);
    }

    private double ddx(double t, double a)
    {
        return -a * Math.cos(t);
    }

    private double ddy(double t, double b)
    {
        return -b * Math.sin(t);
    }

    private double radius(double t, double a, double b)
    {
        return Math.pow(dx(t, a) * dx(t, a) + dy(t, b) * dy(t, b), 1.5)/Math.abs(dx(t, a) * ddy(t, b) - ddx(t, a) * dy(t, b));
    }
}
