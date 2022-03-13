package com.example.demo1.Action;

import javafx.geometry.Point2D;

public class RotateAction extends Action
{
    private Point2D center;
    private double angle;

    public RotateAction(Point2D center, double angle)
    {
        super();
        this.center = center;
        this.angle = angle;
    }


    @Override
    public Point2D make(Point2D point)
    {
        double cx = center.getX(), cy = center.getY();
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));

        double x = point.getX();
        double y = point.getY();
        double nx = cx + (x - cx) * cos - (y - cy) * sin;
        double ny = cy + (x - cx) * sin + (y - cy) * cos;
        return  new Point2D(nx, ny);
    }

    @Override
    public Point2D makeBack(Point2D point)
    {
        return new RotateAction(center, -angle).make(point);
    }
}
