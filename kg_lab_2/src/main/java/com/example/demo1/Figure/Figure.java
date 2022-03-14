package com.example.demo1.Figure;


import com.example.demo1.Action.Action;
import com.example.demo1.Action.MoveAction;
import com.example.demo1.Action.RotateAction;
import com.example.demo1.Action.ScaleAction;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
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

    public void transform(Action action)
    {
        for (int i = 0; i < nPoints; ++i)
        {
            Point2D point = points.get(i);
            points.set(i, action.make(point));
        }
    }

    public void transformBack(Action action)
    {
        for (int i = 0; i < nPoints; ++i)
        {
            Point2D point = points.get(i);
            points.set(i, action.makeBack(point));
        }
    }

    public void moveToCenter(Canvas aCanvas)
    {
        double biasX = aCanvas.getWidth() / 2;
        double biasY = aCanvas.getHeight() / 2;
        ScaleAction flip = new ScaleAction(new Point2D(0, 0), 1, -1);
        MoveAction move = new MoveAction(biasX, biasY);
        for (int i = 0; i < nPoints; ++i)
        {
            Point2D point = points.get(i);
            points.set(i, move.make(flip.make(point)));
        }
    }
}
