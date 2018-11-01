import java.awt.Point;
import java.awt.Color;

// also make center class that extends CenteredShape
public abstract class CenteredShape extends AbstractShape {
    Point center;
    abstract double getArea();

    public CenteredArea( Color c, Point p ) {

    }
}
