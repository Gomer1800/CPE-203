import java.lang.Math;
import java.awt.*;
import java.awt.Point;
import java.util.List;

public class ConvexPolygon implements Shape {
    private Color myColor;
    private List<Point> myVertices;

    public ConvexPolygon (List<Point> points, Color c) {
        this.myColor = c;
        this.myVertices = points;
        //for (int i=0; i< points.size(); i++) {
          //  myVertices.add(points.get(i));
        // }
    }
    public Color getColor() { return myColor; }
    public void setColor(Color c) { this.myColor = c; }
    public double getArea() {
        double term1 =0; // term1 of determinant
        double term2 =0; //term2 of determinant
        for(int i=0; i< this.myVertices.size(); i++) {
            if(i == this.myVertices.size()-1) {
                term1 = term1 + this.myVertices.get(i).x*this.myVertices.get(0).y;
            }
            else {
                term1 = term1 + this.myVertices.get(i).x* this.myVertices.get(i+1).y;
            }
        }
        for(int i=0; i< this.myVertices.size(); i++) {
            if(i == this.myVertices.size()-1) {
                term2 = term2 + this.myVertices.get(i).y*this.myVertices.get(0).x;
            }
            else {
                term1 = term1 + this.myVertices.get(i).y* this.myVertices.get(i+1).x;
            }
        }
        return 0.5* (term1 - term2);
    }
    public double getPerimeter() {
        double sum = 0;
        for (int i=0; i<this.myVertices.size(); i++) {
            if(i != this.myVertices.size()-1) {
                sum = sum + Math.hypot( this.myVertices.get(i+1).getX() - this.myVertices.get(i).getX() , this.myVertices.get(i+1).getY() - this.myVertices.get(i).getY() );
            }
            else {
                sum = sum + Math.hypot( this.myVertices.get(0).getX() - this.myVertices.get(i).getX() , this.myVertices.get(0).getY() - this.myVertices.get(i).getY() );
            }
        }
        return sum;
    }
    public void translate( Point p) {
        for(int i=0; i<this.myVertices.size(); i++) {
            this.myVertices.get(i).translate(p.x, p.y);
        }
    }

    public Point getVertex(int index) {
        return this.myVertices.get(index);
    }
}
