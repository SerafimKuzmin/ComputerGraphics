package com.example.demo1.Action;

import javafx.geometry.Point2D;

public class ScaleAction extends Action
{
    private Point2D center;
    private double kx, ky;

    public ScaleAction(Point2D center, double kx, double ky)
    {
        super();
        this.center = center;
        this.kx = kx;
        this.ky = ky;
    }

    @Override
    public Point2D make(Point2D point)
    {
        double cx = center.getX(), cy = center.getY();
        double x = point.getX();
        double y = point.getY();
        double nx = (x - cx) * kx + cx;
        double ny = (y - cy) * ky + cy;
        return new Point2D(nx, ny);
    }

    @Override
    public Point2D makeBack(Point2D point)
    {
        return new ScaleAction(center, 1 / kx, 1 / ky).make(point);
    }
}
