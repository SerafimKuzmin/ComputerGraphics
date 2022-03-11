package EditComponent;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import GraphicComponent.*;
import GraphicComponent.Point;

public class EditComponent extends JPanel
{
    private static int DEFAULT_WIDTH = 20;
    private static int DEFAULT_HEIGHT = 50;

    private static final Vector<String> columnNames = new Vector<String>();
    static
    {
        columnNames.add("Номер точки");
        columnNames.add("X");
        columnNames.add("Y");
    }
    private TableComponent table;


    public EditComponent(GraphicComponent g, int width, int height)
    {
        DEFAULT_WIDTH = width / 3;
        DEFAULT_HEIGHT = (height / 10) * 9;

        addTable(g);

        var addButton = new JButton("Добавить точку");
        addButton.addActionListener(new AddButtonAction());
        var removeButton = new JButton("Удалить точку");
        removeButton.addActionListener(new RemoveButtonAction());
        var resultButton = new JButton("Получить результат");
        resultButton.addActionListener(event -> {g.makeTask();});


        add(addButton, BorderLayout.CENTER);
        add(removeButton, BorderLayout.CENTER);
        add(resultButton, BorderLayout.SOUTH);
    }


    private class AddButtonAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int nextId = table.getRowCount() > 0 ? Integer.valueOf((String)table.getValueAt(table.getRowCount() - 1, 0)) + 1 : 1;
            table.addPoint(nextId);
        }

    }

    private class RemoveButtonAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getTableSize())
            {
                int selectedRow = table.getSelectedRow();
                table.removeRow(selectedRow);
            }
        }

    }

    @Override
    public void paintComponent(Graphics g)
    {
        var g2 = (Graphics2D) g;
        g2.drawString("", 0, 0);
    }


    @Override 
    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    private void addTable(GraphicComponent g)
    {
        var data = getTableData(g.getPoints());
        var tableModel = new DefaultTableModel(data, columnNames);

        table = new TableComponent(g, tableModel, data);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT * 4 / 5);
        add(scrollPane, BorderLayout.NORTH);
    }


    // получение таблицы значений X и Y каждой точки
    private static Vector<Vector<String>> getTableData(ArrayList<Point> points)
    {
        var resVector = new Vector<Vector<String>>();

        for (int i = 0; i < points.size(); ++i)
        {
            var newRow = new Vector<String>();
            newRow.add(String.valueOf(i + 1));
            newRow.add(String.valueOf(points.get(i).getX()));
            newRow.add(String.valueOf(points.get(i).getY()));
            resVector.add(newRow);

        }
        return resVector;
    }
}