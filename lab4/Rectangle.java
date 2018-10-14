import java.lang.Math;
import java.awt.*;
import java.awt.Point;
import java.util.List;

public  class Rectangle implements Shape {
    private Point topLeft;
    private double width, height;
    private Color color;

    public Rectangle(double width, double height,
            Point point, Color color) {
        this.width = width;
        this.height = height;
        this.topLeft = point;
        this.color = color;
    }

    public Color getColor() { return color; }
    public void setColor(Color c) { color = c; }
    public double getArea() { return width * height; }
    public double getPerimeter() { return 2* (width + height); }
    public void translate(Point p) { topLeft.translate(p.x, p.y); }

    public double getWidth() { return width; }
    public void setWidth(double w) { width = w; }
    public double getHeight() { return height; }
    public void setHeight (double h) { height = h; }
    public Point getTopLeft() { return topLeft; }
}
