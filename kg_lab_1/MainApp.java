import java.awt.EventQueue;

import MainFrame.MainFrame;

public class MainApp
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> 
        {
            new MainFrame();
        });
    }
}
