package MainFrame;

import java.awt.*;

import javax.swing.*;

import EditComponent.*;
import InfoComponent.*;
import GraphicComponent.*;

public class MainFrame extends JFrame
{
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String titleName = "Лабораторная работа №1";
    private GridBagConstraints constraints;

    private GraphicComponent graphicComponent = null;
    
    private void setSettings()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((int)(screenSize.getWidth()) - 200, (int)(screenSize.getHeight()) - 200);
        setTitle(titleName);
        setLocation((int)(screenSize.getWidth() / 5), 0);
        setLocationByPlatform(false);
        setLayout(new GridBagLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        constraints = new GridBagConstraints(); 
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }


    public MainFrame()
    {
        setSettings();
        addInfo();
        addGrahpics();
        addEdit();
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void addInfo()
    {
        setJMenuBar(new InfoComponent());
    }
    
    private void addGrahpics()
    {
        // графика
        constraints.ipady = 30;
        constraints.gridy = 0;

        graphicComponent = new GraphicComponent(getSize().width, getSize().height);
        add(graphicComponent, constraints);
    }


    private void addEdit()
    {
        constraints.ipady = 10;
        constraints.gridy = 0;
        constraints.gridx = 1;
        add(new EditComponent(graphicComponent, getSize().width, getSize().height));
    }

}
