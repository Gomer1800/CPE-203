import java.awt.Color;

public abstract class AbstractShape {
    protected Color color; // protected is not public, but subclasses have access

    // These methods presumably would have implementation
    public Color getColor() { return this.color; }
    public void setColor( Color c ) { 
        this.color = c; 
    }

    protected AbstractShape( Color c) { this.color = c; } // making the constructor of an abstract class protected prevents sub-classes from overriding constructor

    // abstract method
    public abstract double getArea(); // no implementation
}
