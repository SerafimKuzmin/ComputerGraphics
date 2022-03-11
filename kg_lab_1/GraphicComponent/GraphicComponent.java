package GraphicComponent;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class GraphicComponent extends JComponent
{
    private static int DEFAULT_WIDTH = 20;
    private static int DEFAULT_HEIGHT = 50;
    private ArrayList<Point> points = new ArrayList<Point>();
    private Result result = new Result();
    private boolean isResult = false;

    public GraphicComponent(int width, int height)
    {
        DEFAULT_WIDTH = width * 2 / 3;
        DEFAULT_HEIGHT = (height / 10) * 9;
    }

    private void flip(Graphics g)
    {
        var g2 = (Graphics2D) g;
        // flip Y-axis
        g2.translate(10, DEFAULT_HEIGHT);
        g2.scale(1,-1);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        var g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        flip(g2);
        g2.setPaint(Color.WHITE);
        g2.fill(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        if (isResult == true)
        {
            printResult(g2);
        }
    }

    @Override 
    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public ArrayList<Point> getPoints()
    {
        return points;
    }

    public void addPoint(Point point)
    {
        points.add(point);
    }

    public void setResult(boolean isResult) 
    {
        this.isResult = isResult;
    }

    public void makeTask()
    {
        try
        {
            this.isResult = true;
            calculateResult();
            repaint();
        } 
        catch (LackOfDataException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void printPoints(Graphics2D g, Point ... points)
    {
        g.setPaint(Color.BLACK);

        for (var point : points)
        {
            g.fill(point);
        }

        flip(g);
        for (var point : points)
        {
            g.drawString(String.format("%.2f %.2f", point.getRealX(), point.getRealY()), point.getIntX() - 10, DEFAULT_HEIGHT - point.getIntY() - 10);
        }
        flip(g);
    }

    private void printAnnotation(Graphics2D g)
    {
        flip(g);
        double inArea = result.getInCircle().getArea();
        double outArea = result.getOutCircle().getArea();
        
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString(String.format("Площадь вписанного круга = %.4f", inArea), 0, 15);
        g.drawString(String.format("Площадь описанного круга = %.4f", outArea), 0, 30);
        g.drawString(String.format("Разность площадей = %.4f", outArea - inArea), 0, 45);
        flip(g);
    }

    private void printResult(Graphics2D g)
    {
        stabResult();

        Circle outCircle = result.getOutCircle();
        g.setPaint(Color.YELLOW);
        g.fill(outCircle);

        Triangle triangle = result.getTriangle();
        g.setPaint(Color.GREEN);
        g.fill(triangle);

        Circle inCircle = result.getInCircle();
        g.setPaint(Color.RED);
        g.fill(inCircle);

        printPoints(g, triangle.firstPoint, triangle.secondPoint, triangle.thirdPoint);
        printAnnotation(g);
    }

    private void calculateResult() throws LackOfDataException
    {
        result = new Result();
        if (points.size() < 3)
        {
            setResult(false);
            throw new LackOfDataException("Недостаточно точек даже для одного треугольника!");
        }

        System.out.println(points.size());
        for (int i = 0; i < points.size() - 2; ++i)
        {
            for (int j = i + 1; j < points.size() - 1; ++j)
            {
                for (int k = j + 1; k < points.size(); ++k)
                {
                    Point first = points.get(i), second = points.get(j), third = points.get(k);
                    result = result.checkPoints(first, second, third);
                }
            }
        }

        if (result.getOutCircle().getRadius() == Double.MAX_VALUE)
        {
            setResult(false);
            throw new LackOfDataException("Заданные точки не образуют треугольник!");
        }
    }

    private void stabResult()
    {
        moveResToCent();

        scaleRes();
    }

    private void moveResToCent()
    {
        Point vec = new Point(DEFAULT_WIDTH / 2, (DEFAULT_HEIGHT - 20) / 2, 0, 0).minus(result.getOutCircle().getCenter());
        result.moveTo(vec);
    }

    private void scaleRes()
    {
        Point center  = new Point(DEFAULT_WIDTH / 2, (DEFAULT_HEIGHT - 20) / 2, 0, 0);
        double k = (DEFAULT_HEIGHT - 40) / (result.getOutCircle().getRadius() * 2);
        result.scale(k, center);
    }

    class LackOfDataException extends IOException
    {
        public LackOfDataException() {}
        public LackOfDataException(String gripe)
        {
            super(gripe);
        }
    }
}
