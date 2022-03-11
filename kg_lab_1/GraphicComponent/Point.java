package GraphicComponent;

import java.awt.geom.*;


public class Point extends Ellipse2D.Double implements Resizable
{
    public static final double EPS = 1e-5;
    public static final double SIZE = 10;
    private double x, y;
    private double realX, realY;

    public Point(double x, double y, double realX, double realY)
    {
        super(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
        this.x = x;
        this.y = y;
        this.realX = realX;
        this.realY = realY;
    }

    public void setIntX(double x)
    {
        this.x = x;
    }

    public void setIntY(double y)
    {
        this.y = y;
    }

    public double getDoubleX()
    {
        return x;
    }

    public double getDoubleY()
    {
        return y;
    }

    public int getIntX()
    {
        return (int)x;
    }

    public int getIntY()
    {
        return (int)y;
    }

    public double getRealX()
    {
        return realX;
    }

    public double getRealY()
    {
        return realY;
    }

    public static double lenTo(Point first, Point second)
    {
        return Math.sqrt(Math.pow(first.x - second.x, 2) + Math.pow(first.y - second.y, 2));
    }
    
    public static double vecProd(Point first, Point second) 
    {
        return first.x * second.y - second.x * first.y;
    }

    public Point minus(Point point)
    {
        return new Point(x - point.x, y - point.y, x, y);
    }

    public Point moveTo(Point vec)
    {
        double newX = x + vec.x;
        double newY = y + vec.y;
        return new Point(newX, newY, realX, realY);
    }

    public Point scale(double k, Point center)
    {
        double newX = x * k + (1 - k) * center.x;
        double newY = y * k + (1 - k) * center.y;
        return new Point(newX, newY, realX, realY);
    }
}
