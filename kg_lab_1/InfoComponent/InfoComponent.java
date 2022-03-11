package InfoComponent;

import javax.swing.*;

public class InfoComponent extends JMenuBar
{
    private static final String info = "На плоскости дано множество точек.\nНайти такой треугольник с вершинами в этих точках,\nдля которого разность площадей описанного и вписанного кругов минимальна.";

    public InfoComponent()
    {
        super();
        var infoMenu = new JMenuItem("Условие");
        infoMenu.addActionListener(event -> {System.out.println("jopa");
        JOptionPane.showMessageDialog(null, info, "Условие", JOptionPane.INFORMATION_MESSAGE);});
        add(infoMenu);
    }

}
