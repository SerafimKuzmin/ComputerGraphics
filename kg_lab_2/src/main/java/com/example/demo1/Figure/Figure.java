package com.example.demo1.Figure;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Figure
{
    public static final double EPS = 1e-3;
    private  ArrayList <Point2D> points;
    private int nPoints;

    public Figure()
    {
        nPoints = 0;
        points = new ArrayList<>();
    }

    public void addPoint(double x, double y)
    {
        points.add(new Point2D(x, y));
        nPoints++;
    }

    public void addPoint(Point2D point)
    {
        points.add(point);
        nPoints++;
    }

    public void draw(GraphicsContext g)
    {
        g.moveTo(points.get(0).getX(), points.get(0).getY());
        for (int i = 1; i < nPoints; i++)
        {
            g.lineTo(points.get(i).getX(), points.get(i).getY());
        }
    }

    public void move(double dx, double dy)
    {
        for (int i = 0; i < nPoints; ++i)
        {
            Point2D point = points.get(i);
            Point2D newPoint = new Point2D(point.getX() + dx, point.getY() + dy);
            points.set(i, newPoint);
        }
    }

    public void rotate(Point2D center, double angle)
    {
        double cx = center.getX(), cy = center.getY();
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));

        for (int i = 0; i < nPoints; ++i)
        {
            double x = points.get(i).getX();
            double y = points.get(i).getY();
            double nx = cx + (x - cx) * cos - (y - cy) * sin;
            double ny = cy + (x - cx) * sin + (y - cy) * cos;
            points.set(i, new Point2D(nx, ny));
        }
    }

    public void scale(Point2D center, double kx, double ky)
    {
        double cx = center.getX(), cy = center.getY();

        for (int i = 0; i < nPoints; ++i)
        {
            double x = points.get(i).getX();
            double y = points.get(i).getY();
            double nx = (x - cx) * kx + cx;
            double ny = (y - cy) * ky + cy;
            points.set(i, new Point2D(nx, ny));
        }
    }
}
