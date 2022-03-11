package GraphicComponent;

import java.awt.*;

public interface Resizable
{
    public Shape moveTo(Point vec);
    public Shape scale(double k, Point center);
}
