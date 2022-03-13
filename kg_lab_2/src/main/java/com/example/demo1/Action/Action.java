package com.example.demo1.Action;

import javafx.geometry.Point2D;

public abstract class Action
{
    public abstract Point2D make(Point2D point);
    public abstract Point2D makeBack(Point2D point);
}
