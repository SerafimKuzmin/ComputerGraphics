package com.example.demo1;

import com.example.demo1.Action.*;
import com.example.demo1.Figure.*;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
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
        moveToCenter();

        drawPrint();
        TranslationLogger.launchTranslation(translationLogger);
        drawCenter();
    }

    private void drawAxises()
    {
        NumberAxis xAxis = new NumberAxis(-500, 500, 100);
        NumberAxis yAxis = new NumberAxis(-500, 500, 100);
        
    }

    private void initPrint()
    {
        figures = new ArrayList<>();

        // основание дома
        {
            var basement = new MyRectangle(new Point2D(100, 100), 100, 100);
            figures.add(basement);
            var roof = new MyTriangle(new Point2D(100, 100), new Point2D(130, 60), new Point2D(200, 100));
            figures.add(roof);
        }

        // большое окно
        {
            var bigWindowFrame = new MyRectangle(new Point2D(110, 130), 40, 40);
            figures.add(bigWindowFrame);
            var bigWindowFramesUp = new MyEllipseUpperArc(new Point2D(110, 120), 40, 20);
            figures.add(bigWindowFramesUp);
            var bigWindowFramesGirder = new MyLine(new Point2D(130, 120), new Point2D(130, 170));
            figures.add(bigWindowFramesGirder);
        }

        // маленькое окно
        {
            var littleWindowFrame = new MyEllipse(new Point2D(165, 120), 20, 50);
            figures.add(littleWindowFrame);
            var littleWindowFramesFirstGrider = new MyLine(new Point2D(175, 120), new Point2D(175, 170));
            figures.add(littleWindowFramesFirstGrider);
            var littleWindowFramesSecondGrider = new MyLine(new Point2D(165, 145), new Point2D(185, 145));
            figures.add(littleWindowFramesSecondGrider);

        }

        // форточка
        {
            var flyFrame = new MyCircle(new Point2D(135, 85), 10);
            figures.add(flyFrame);
            var flyFrameFirstLine = new MyLine(new Point2D(125, 85), new Point2D(135, 95));
            figures.add(flyFrameFirstLine);
            // var flyFrameSecondLine = new MyLine(new Point2D(125, 82), new Point2D(137, 94));
            // figures.add(flyFrameSecondLine);
            var flyFrameThirdLine = new MyLine(new Point2D(127, 79), new Point2D(141, 93));
            figures.add(flyFrameThirdLine);
            var flyFrameFourthLine = new MyLine(new Point2D(129, 77), new Point2D(143, 91));
            figures.add(flyFrameFourthLine);
            // var flyFrameFivethLine = new MyLine(new Point2D(132, 75), new Point2D(144, 87));
            // figures.add(flyFrameFivethLine);
            var flyFrameSixthLine = new MyLine(new Point2D(134, 75), new Point2D(145, 85));
            figures.add(flyFrameSixthLine);
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
        g.fillOval(dx + x - pointWidth/2, dy - y + pointHeight/2, pointWidth, pointHeight);
        g.fillText(Double.valueOf(x).toString() + " " + Double.valueOf(y).toString(), dx + x - 40, dy - y + pointWidth*3);
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
        MoveAction move = new MoveAction(dx, dy);

        return move.make(centerPoint);
    }

}