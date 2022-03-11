package EditComponent;

import GraphicComponent.*;
import GraphicComponent.Point;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class TableComponent extends JTable
{
    private ArrayList<Point> points;
    private DefaultTableModel tableModel;
    private Vector <Vector <String>> data;


    public TableComponent(GraphicComponent g, DefaultTableModel tableModel, Vector <Vector <String>> data)
    {
        super(tableModel);
        this.data = data;
        this.tableModel = tableModel;
        points = g.getPoints();

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public boolean isCellEditable (int row, int column)
    {
        return column != 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if (checkValidity((String)aValue))
        {
            super.setValueAt(aValue, rowIndex, columnIndex);


            double newX = columnIndex == 1 ? Double.valueOf((String)aValue) : points.get(rowIndex).getX();
            double newY = columnIndex == 2 ? Double.valueOf((String)aValue) : points.get(rowIndex).getY();

            var NewPoint = new Point(newX, newY, newX, newY);
            points.set(rowIndex, NewPoint);
            repaint();
        }
    }

    private boolean checkValidity(String value)
    {
        boolean res = true;
        try
        {
            Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Координаты точки должны быть числами!",
                                                "Ошибка", JOptionPane.WARNING_MESSAGE);
            res = false;
        }
        return res;
    }


    public void removeRow(int row)
    {
        points.remove(row);
        tableModel.removeRow(row);
        stabilazeIndex();
    }

    public void addPoint(int nextId)
    {
        var newPoint = new Point(0, 0, 0, 0);
        points.add(newPoint);
        tableModel.addRow(new Object[] {String.valueOf(nextId), "0", "0"});
        stabilazeIndex();
    }

    public int getTableSize()
    {
        return points.size();
    }

    private void stabilazeIndex()
    {
        int i = 0;
        for (var x: data)
        {
            x.set(0, String.valueOf(i + 1));
            i++;
        }
    }
}