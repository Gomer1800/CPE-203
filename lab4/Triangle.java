import java.lang.Math;
import java.awt.*;
import java.awt.Point;
import java.util.List;

public class Triangle implements Shape {
    private Point a,b,c;
    private Color color;

    public Triangle (Point a, Point b, Point c, Color otherColor) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = otherColor;
    }

    public Color getColor() { return color; }
    public void setColor(Color c) { color = c; }
    public double getArea() {
        return 0.5 * Math.abs(a.x*(b.y-c.y) + b.x*(c.y-a.y) +c.x*(a.y-b.y));
    }
    public double getPerimeter() {
        return a.distance(b) + b.distance(c) + c.distance(a);
    }
    public void translate(Point p) {
        a.translate(p.x, p.y);
        b.translate(p.x, p.y);
        c.translate(p.x, p.y);
    }

    public Point getVertexA() { return a; }
    public Point getVertexB() { return b; }
    public Point getVertexC() { return c; }
}
