package GraphicComponent;

import java.awt.geom.*;
import java.lang.Math;

public class Circle extends Ellipse2D.Double implements Resizable
{
    private Point center;
    private double radius;
    private double realRadius;

    public Circle(Point center, double radius, double realRadius)
    {
        super(center.getDoubleX() - radius, center.getDoubleY() - radius, 2 * radius, 2 * radius);
        this.center = center;
        this.radius = radius;
        this.realRadius = realRadius;
    }

    public static Circle getOutCircle(Triangle tr) { return getOutCircle(tr.firstPoint, tr.secondPoint, tr.thirdPoint); }

    public static Circle getInCircle(Triangle tr) { return getInCircle(tr.firstPoint, tr.secondPoint, tr.thirdPoint); }

    public static Circle getOutCircle(Point first, Point second, Point third)
    {
        double A = second.getDoubleX() - first.getDoubleX();
        double B = second.getDoubleY() - first.getDoubleY();
        double C = third.getDoubleX() - first.getDoubleX();
        double D = third.getDoubleY() - first.getDoubleY();
        double E = A * (first.getDoubleX() + second.getDoubleX()) + B * (first.getDoubleY() + second.getDoubleY());
        double F = C * (first.getDoubleX() + third.getDoubleX()) + D * (first.getDoubleY() + third.getDoubleY());
        double G = 2 * (A * (third.getDoubleY() - second.getDoubleY()) - B * (third.getDoubleX() - second.getDoubleX()));
        double Cx = (D * E - B * F) / G;
        double Cy = (A * F - C * E) / G;
        double R = Math.abs(Point.lenTo(first, new Point(Cx, Cy, Cx, Cy)));
        var resCircle = new Circle(new Point(Cx, Cy, Cx, Cy), R, R);

        return resCircle;
    }

    public static Circle getInCircle(Point first, Point second, Point third)
    {
        double xc, yc;

        //найдем точки пересечения 2х биссектрис со сторонами
        //1. найдем длины  сторон
        double A = Point.lenTo(first, second);
        double B = Point.lenTo(third, second);
        double C = Point.lenTo(first, third);
        //2. найдем координаты концов 2х биссектрис
        double x1 = first.getDoubleX(), y1 = first.getDoubleY(), x2 = second.getDoubleX(), y2 = second.getDoubleY(), x3 = third.getDoubleX(), y3 = third.getDoubleY();
        double k = C / A; //коэффициент пропорциональности
        double x11 = (x3 + k * x2) / (1 + k);
        double y11 = (y3 + k * y2) / (1 + k);
        k = B / A;
        double x12 = (x3 + k * x1) / (1 + k);
        double y12 = (y3 + k * y1) / (1 + k);
        //найдем координаты пересечения биссектрис=координаты центра окружности
        if (Math.abs(x1 - x11) < Point.EPS)  //если вдруг первая биссектриса вертикально
        {
            xc = x1;  //х известен
            double k2 = (y12 - y2) / (x12 - x2);  //находим коэффициенты второго уравнения
            double b2 = (x12 * y2 - x2 * y12) / (x12 - x2);
            yc = k2 * xc + b2;  //координату У
        }
        else if (Math.abs(x2 - x12) < Point.EPS) //или вторая
        {
            xc = x2;
            double k1 = (y11 - y1) / (x11 - x1);
            double b1 = (x11 * y1 - x1 * y11) / (x11 - x1);
            yc = k1 * xc + b1;
        }
        else //если обе не вертикальные
        {
            double k1 = (y11 - y1) / (x11 - x1);  //коэффициенты 1 уравнения
            double b1 = (x11 * y1 - x1 * y11) / (x11 - x1);
            double k2 = (y12 - y2) / (x12 - x2);  //второго
            double b2 = (x12 * y2 - x2 * y12) / (x12 - x2);
            xc = (b1 - b2) / (k2 - k1);  //координаты центра
            yc = (k2 * b1 - k1 * b2) / (k2 - k1);
        }

        double R = new Triangle(first, second, third).getArea() / (A + B + C) * 2;
        return new Circle(new Point(xc, yc, xc, yc), R, R);
    }

    @Override
    public Circle moveTo(Point vec) {
        return new Circle(center.moveTo(vec), radius, radius);
    }

    @Override
    public Circle scale(double k, Point center)
    {
        return new Circle(this.center.scale(k, center), radius * k, radius);
    }

    public double getArea()
    {
        return Math.PI * realRadius * realRadius;
    }

    public Point getCenter()
    {
        return center;
    }

    public double getRadius()
    {
        return radius;
    }
}
