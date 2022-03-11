package GraphicComponent;

public class Result
{
    private Circle inCircle, outCircle;
    private Triangle triangle;
    
    public Result()
    {
        inCircle = new Circle(new Point(0, 0, 0, 0), 0, 0);
        outCircle = new Circle(new Point(0, 0, 0, 0), Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public Result(Circle inCircle, Circle outCircle, Triangle triangle)
    {
        this.inCircle = inCircle;
        this.outCircle = outCircle;
        this.triangle = triangle;
    }

    public Circle getInCircle() { return inCircle; }

    public Circle getOutCircle() { return outCircle; }
    
    public Triangle getTriangle() { return triangle; }

    public void moveTo(Point vec)
    {
        inCircle = inCircle.moveTo(vec);
        outCircle = outCircle.moveTo(vec);
        triangle = triangle.moveTo(vec);
    }

    public void scale(double k, Point center)
    {
        inCircle = inCircle.scale(k, center);
        outCircle = outCircle.scale(k, center);
        triangle = triangle.scale(k, center);  
    }

    public Result checkPoints(Point first, Point second, Point third)
    {
        if (Triangle.isTriangleExist(first, second, third))
        {
            return checkTriangle(new Triangle(first, second, third));
        }

        return this;
    }

    private Result checkTriangle(Triangle tr)
    {
        Circle curInCircle, curOutCircle;
        curInCircle = Circle.getInCircle(tr);
        curOutCircle = Circle.getOutCircle(tr);

        if (curOutCircle.getRadius() - curInCircle.getRadius() < this.outCircle.getRadius() - this.inCircle.getRadius())
        {
            return new Result(curInCircle, curOutCircle, tr);
        }
        return this;
    }
}
