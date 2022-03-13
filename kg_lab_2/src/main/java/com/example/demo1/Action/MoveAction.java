package com.example.demo1.Action;

import javafx.geometry.Point2D;

public class MoveAction extends Action
{
    private double dx, dy;

    public MoveAction(double dx, double dy)
    {
        super();
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public Point2D make(Point2D point)
    {
        return new Point2D(point.getX() + dx, point.getY() + dy);
    }

    @Override
    public Point2D makeBack(Point2D point)
    {
        return new Point2D(point.getX() - dx, point.getY() - dy);
    }
}
