package GraphicComponent;

import java.awt.geom.*;

public class Triangle extends Path2D.Double implements Resizable
{
    Point firstPoint, secondPoint, thirdPoint;

    private void createTriangle()
    {
        moveTo(firstPoint.getDoubleX(), firstPoint.getDoubleY());
        lineTo(secondPoint.getDoubleX(), secondPoint.getDoubleY());
        lineTo(thirdPoint.getDoubleX(), thirdPoint.getDoubleY());
        lineTo(firstPoint.getDoubleX(), firstPoint.getDoubleY());
    }

    public Triangle(Point firstPoint, Point secondPoint, Point thirdPoint)
    {
        super();
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.thirdPoint = thirdPoint;
        createTriangle();
    }

    public static boolean isTriangleExist(Point firstPoint, Point secondPoint, Point thirdPoint)
    {
        Point vecFT = thirdPoint.minus(firstPoint);
        Point vecST = thirdPoint.minus(secondPoint);

        return Math.abs(Point.vecProd(vecFT, vecST)) > Point.EPS;
    }

    public double getArea()
    {
        Point vecFT = thirdPoint.minus(firstPoint);
        Point vecST = thirdPoint.minus(secondPoint);

        return Math.abs(Point.vecProd(vecFT, vecST)) / 2;
    }

    @Override
    public Triangle moveTo(Point vec) 
    {
        return new Triangle(firstPoint.moveTo(vec), secondPoint.moveTo(vec), thirdPoint.moveTo(vec));
    }

    @Override
    public Triangle scale(double k, Point center) 
    {
        Point newFirstPoint = firstPoint.scale(k, center);
        Point newSecondPoint = secondPoint.scale(k, center);
        Point newThirdPoint = thirdPoint.scale(k, center);
        return new Triangle(newFirstPoint, newSecondPoint, newThirdPoint);
    }
}
