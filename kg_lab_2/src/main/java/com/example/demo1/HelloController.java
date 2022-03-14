package com.example.demo1;

import com.example.demo1.Action.Action;
import com.example.demo1.Action.MoveAction;
import com.example.demo1.Action.RotateAction;
import com.example.demo1.Action.ScaleAction;
import com.example.demo1.Figure.Figure;
import com.example.demo1.Figure.MyCircle;
import com.example.demo1.Figure.MyLine;
import com.example.demo1.Figure.MyTriangle;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.*;

import java.util.ArrayList;
import java.util.Stack;

public class HelloController{

    private ArrayList <Figure> figures;

    private ArrayList <Figure> axises;

    private Stack <Action> actions;

    @FXML
    Canvas aCanvas;

    @FXML
    private Canvas aCanvasForCenter;

    @FXML
    private TextField moveX;

    @FXML
    private TextField moveY;

    @FXML
    private TextField scaleValueX;

    @FXML
    private TextField scaleValueY;

    @FXML
    private TextField centerX;

    @FXML
    private TextField centerY;

    @FXML
    private TextField rotateValue;

    @FXML
    private TextArea translationLogger;


    @FXML
    private void initialize()
    {
        actions = new Stack<>();
        initPrint();
        moveToCenter();
        drawPrint();
        TranslationLogger.launchTranslation(translationLogger);
        drawAxises();
    }

    private void makeAxises()
    {
        double width = aCanvas.getWidth();
        double height = aCanvas.getHeight();
        axises = new ArrayList<>();
        MyLine yAxis = new MyLine(new Point2D(0, -height / 2), new Point2D(0, height / 2));
        MyLine xAxis = new MyLine(new Point2D(-width / 2, 0), new Point2D(width / 2, 0));
        axises.add(xAxis);
        axises.add(yAxis);

        var xTr = new MyTriangle(new Point2D(width / 2, 0), new Point2D(width / 2 - 15, 5), new Point2D(width / 2 - 15, -5));
        var yTr = new MyTriangle(new Point2D(0, height / 2), new Point2D(5, height / 2 - 15), new Point2D(-5, height / 2 - 15));
        axises.add(xTr);
        axises.add(yTr);

        for (var figure : axises)
            figure.moveToCenter(aCanvasForCenter);
    }

    private void drawAxises()
    {
        drawCenter();
        var gc = aCanvasForCenter.getGraphicsContext2D();
        makeAxises();
        for (var figure : axises)
            figure.draw(gc);

        double width = aCanvas.getWidth();
        double height = aCanvas.getHeight();
        for (double i = -width / 2 + 100; i <= width / 2 - 100; i += 100)
            drawPointWithCoord(i, 0, i);
        for (double i = -height / 2 + 100; i <= height / 2 - 100; i += 100)
            drawPointWithCoord(0, i, i);
    }

    private void initPrint()
    {
        figures = new ArrayList<>();

        // отрисовка двух главных окружностей
        {
            figures.add(new MyCircle(0, 0, 10));
            figures.add(new MyCircle(0, 0, 80));
            figures.add(new MyCircle(0, 0, 100));
        }

        // отрисовка линий через 2 главные окрыжности
        {
            double d = 80 * Math.sin(Math.toRadians(45));
            var line = new MyLine(new Point2D(-d, -d), new Point2D(d, d));
            figures.add(line);
            var line1 = new MyLine(new Point2D(d, -d), new Point2D(-d, d));
            figures.add(line1);
        }

        // отрисовка 4-х маленьких окружностей
        {
            var circle3 = new MyCircle(0, -90, 10);
            figures.add(circle3);
            var circle4 = new MyCircle(0, 90, 10);
            figures.add(circle4);
            var circle5 = new MyCircle(-90, 0, 10);
            figures.add(circle5);
            var circle6 = new MyCircle(90, 0, 10);
            figures.add(circle6);

            // отрисовка линий через маленькие окружности
            {
                double d = 10 * Math.sin(Math.toRadians(45));
                figures.add(new MyLine(new Point2D(-d, -90 - d), new Point2D(d, -90 + d)));
                figures.add(new MyLine(new Point2D(-d, -90 + d), new Point2D(d, -90 - d)));
                figures.add(new MyLine(new Point2D(- d, 90 - d), new Point2D(d, 90 + d)));
                figures.add(new MyLine(new Point2D(- d, 90 + d), new Point2D(d, 90 - d)));
                figures.add(new MyLine(new Point2D(-90 - d, - d), new Point2D(-90 + d, d)));
                figures.add(new MyLine(new Point2D(-90 - d, d), new Point2D(-90 + d, -d)));
                figures.add(new MyLine(new Point2D(90 - d, - d), new Point2D(90 + d, d)));
                figures.add(new MyLine(new Point2D(90 - d, d), new Point2D(90 + d, -d)));
            }
        }

        // отрисовка двух треугольников
        {
            var triangle = new MyTriangle(new Point2D(0, 0), new Point2D(-100, -200), new Point2D(-60, -200));
            figures.add(triangle);
            var triangle1 = new MyTriangle(new Point2D(0, 0), new Point2D(60, -200), new Point2D(100, -200));
            figures.add(triangle1);
        }
    }

    private void moveToCenter()
    {
        for (var figure : figures)
            figure.moveToCenter(aCanvas);
    }

    @FXML
    private void changedCenter()
    {
        try
        {
            double currentCenterX = Double.parseDouble(centerX.getText());
            double currentCenterY = Double.parseDouble(centerY.getText());
            var gc = aCanvasForCenter.getGraphicsContext2D();
            gc.clearRect(0, 0, aCanvasForCenter.getWidth(), aCanvasForCenter.getHeight());
            drawAxises();
            drawPointWithCoord(currentCenterX, currentCenterY);
        }
        catch (NullPointerException e)
        {
            showAlertWithMSG("Некорректно задан центр");
        }
        catch (NumberFormatException e)
        {
            showAlertWithMSG("Некорректный ввод числа, попробуйте еще раз!");
        }
    }

    private void drawCenter()
    {
        try
        {
            double currentCenterX = Double.parseDouble(centerX.getText());
            double currentCenterY = Double.parseDouble(centerY.getText());
            drawPointWithCoord(currentCenterX, currentCenterY);
        }
        catch (NullPointerException e)
        {
            showAlertWithMSG("Некорректно задан центр");
        }
    }

    private void drawPointWithCoord(double x, double y)
    {
        final double pointHeight = 5;
        final double pointWidth = 5;
        final double dx = aCanvas.getWidth() / 2;
        final double dy = aCanvas.getHeight() / 2;
        var g = aCanvasForCenter.getGraphicsContext2D();
        g.fillOval(dx + x - pointWidth/2, dy - y - pointHeight/2, pointWidth, pointHeight);
        g.fillText(Double.valueOf(x).toString() + " " + Double.valueOf(y).toString(), dx + x - 40, dy - y + pointWidth*3);
        g.stroke();
    }

    private void drawPointWithCoord(double x, double y, double value)
    {
        final double pointHeight = 5;
        final double pointWidth = 5;
        final double dx = aCanvas.getWidth() / 2;
        final double dy = aCanvas.getHeight() / 2;
        var g = aCanvasForCenter.getGraphicsContext2D();
        g.fillOval(dx + x - pointWidth/2, dy - y - pointHeight/2, pointWidth, pointHeight);
        g.fillText(Double.valueOf(value).toString(), dx + x - 40, dy - y + pointWidth*3);
        g.stroke();
    }

    public void drawPrint()
    {
        var gc = aCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, aCanvas.getWidth(), aCanvas.getHeight());
        gc.beginPath();

        for (var figure : figures)
            figure.draw(gc);

        gc.stroke();
    }

    @FXML
    private void moveFigures()
    {
        try
        {
            if (moveX.getText().length() == 0 ||
                moveY.getText().length() == 0 ||
                centerY.getText().length() == 0)
            {
                throw new NullPointerException();
            }

            double dx = Double.parseDouble(moveX.getText());
            double dy = Double.parseDouble(moveY.getText());
            Action action = new MoveAction(dx, -dy);
            actions.add(action);

            for (var figure : figures)
            {
                figure.transform(action);
            }
            drawPrint();

            TranslationLogger.transformLogger(translationLogger, new Point2D(dx, dy));
        }
    catch (NumberFormatException e)
    {
        showAlertWithMSG("Некорректный ввод числа, попробуйте еще раз!");
    }
    catch (NullPointerException e)
    {
        showAlertWithMSG("Вы не ввели одно из значений, попробуйте еще раз!");
    }

    }

    @FXML
    private void scaleFigures()
    {
        try
        {
            if (scaleValueX.getText().length() == 0 ||
                scaleValueY.getText().length() == 0 ||
                centerX.getText().length() == 0 ||
                centerY.getText().length() == 0)
            {
                throw new NullPointerException();
            }

            var center = getCenter();
            double kx = Double.parseDouble(scaleValueX.getText());
            double ky = Double.parseDouble(scaleValueY.getText());
            if (kx == 0 || ky == 0)
                throw new NullPointerException();

            Action action = new ScaleAction(center, kx, ky);
            actions.add(action);

            for (var figure : figures)
            {
                figure.transform(action);
            }
            drawPrint();

            TranslationLogger.scaleLogger(translationLogger, new Point2D(kx, ky));
        }
        catch (NumberFormatException e)
        {
            showAlertWithMSG("Некорректный ввод, попробуйте еще раз!");
        }
        catch (NullPointerException e)
        {
            showAlertWithMSG("Вы не ввели одно из значений, попробуйте еще раз!");
        }
    }

    @FXML
    private void rotateFigures()
    {
        try
        {
            if (rotateValue.getText().length() == 0 ||
                centerX.getText().length() == 0 ||
                centerY.getText().length() == 0)
            {
                throw new NullPointerException();
            }

            double angle = Double.parseDouble(rotateValue.getText());
            var center = getCenter();
            Action action = new RotateAction(center, angle);
            actions.add(action);

            for (var figure : figures)
            {
                figure.transform(action);
            }
            drawPrint();

            TranslationLogger.rotateLogger(translationLogger, angle);

        }
        catch (NumberFormatException e)
        {
            showAlertWithMSG("Некорректный ввод числа, попробуйте еще раз!");
        }
        catch (NullPointerException e)
        {
            showAlertWithMSG("Вы не ввели одно из значений, попробуйте еще раз!");
        }
    }

    @FXML
    private void goBack()
    {
        if (actions.size() == 0)
        {
            showAlertWithMSG("Вы вернулись в исходное состояние, невозможно сделать шаг назад");
        }
        else
        {
            Action action = actions.pop();

            for (var figure : figures)
                figure.transformBack(action);
            drawPrint();

            TranslationLogger.removeLastTranslation(translationLogger);
        }
    }

    private void showAlertWithMSG(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.setTitle("Предупреждение");
        alert.showAndWait();
    }

    private Point2D getCenter()
    {
        var centerPoint = new Point2D(Double.parseDouble(centerX.getText()),
                                Double.parseDouble(centerY.getText()));
        final double dx = aCanvas.getWidth() / 2;
        final double dy = aCanvas.getHeight() / 2;
        ScaleAction scale = new ScaleAction(new Point2D(0, 0), 1, -1);
        MoveAction move = new MoveAction(dx, dy);

        return move.make(scale.make(centerPoint));
    }

}