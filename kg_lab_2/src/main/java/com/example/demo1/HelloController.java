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
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.transform.*;

import java.util.ArrayList;
import java.util.Stack;

public class HelloController{

    private ArrayList <Figure> figures;

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
        drawPrint();
        TranslationLogger.launchTranslation(translationLogger);
        drawCenter();
    }

    private void initPrint()
    {
        figures = new ArrayList<>();

        // отрисовка двух главных окружностей
        {
            var circle = new MyCircle(200, 200, 10);
            figures.add(circle);
            var circle1 = new MyCircle(200, 200, 80);
            figures.add(circle1);
            var circle2 = new MyCircle(200, 200, 100);
            figures.add(circle2);
        }

        // отрисовка линий через 2 главные окрыжности
        {
            double d = 80 * Math.sin(Math.toRadians(45));
            var line = new MyLine(new Point2D(200 - d, 200 - d), new Point2D(200 + d, 200 + d));
            figures.add(line);
            var line1 = new MyLine(new Point2D(200 + d, 200 - d), new Point2D(200 - d, 200 + d));
            figures.add(line1);
        }

        // отрисовка 4-х маленьких окружностей
        {
            var circle3 = new MyCircle(200, 110, 10);
            figures.add(circle3);
            var circle4 = new MyCircle(200, 290, 10);
            figures.add(circle4);
            var circle5 = new MyCircle(110, 200, 10);
            figures.add(circle5);
            var circle6 = new MyCircle(290, 200, 10);
            figures.add(circle6);

            // отрисовка линий через маленькие окружности
            {
                double d = 10 * Math.sin(Math.toRadians(45));
                figures.add(new MyLine(new Point2D(200 - d, 110 - d), new Point2D(200 + d, 110 + d)));
                figures.add(new MyLine(new Point2D(200 - d, 110 + d), new Point2D(200 + d, 110 - d)));
                figures.add(new MyLine(new Point2D(200 - d, 290 - d), new Point2D(200 + d, 290 + d)));
                figures.add(new MyLine(new Point2D(200 - d, 290 + d), new Point2D(200 + d, 290 - d)));
                figures.add(new MyLine(new Point2D(110 - d, 200 - d), new Point2D(110 + d, 200 + d)));
                figures.add(new MyLine(new Point2D(110 - d, 200 + d), new Point2D(110 + d, 200 - d)));
                figures.add(new MyLine(new Point2D(290 - d, 200 - d), new Point2D(290 + d, 200 + d)));
                figures.add(new MyLine(new Point2D(290 - d, 200 + d), new Point2D(290 + d, 200 - d)));
            }
        }

        // отрисовка двух треугольников
        {
            var triangle = new MyTriangle(new Point2D(200, 200), new Point2D(100, 400), new Point2D(140, 400));
            figures.add(triangle);
            var triangle1 = new MyTriangle(new Point2D(200, 200), new Point2D(260, 400), new Point2D(300, 400));
            figures.add(triangle1);
        }
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
            drawPointWithCoord(currentCenterX, currentCenterY);
        }
        catch (NullPointerException e)
        {
            showAlertWithMSG("Некорректно задан центр");
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
        var g = aCanvasForCenter.getGraphicsContext2D();
        g.fillOval(x - pointWidth/2, y - pointHeight/2, pointWidth, pointHeight);
        g.fillText(Double.valueOf(x).toString() + " " + Double.valueOf(y).toString(), x - 40, y - pointWidth*3);
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
            Action action = new MoveAction(dx, dy);
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

        //centerPoint = centerPoint.add(centerShift);

        return centerPoint;
    }

}