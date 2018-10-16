import java.lang.Math;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Workspace {
    private List<Shape> myShapes = new ArrayList<Shape>();

    public Workspace (List<Shape> otherShapes) {
        myShapes = otherShapes;
    }
    public Workspace() {
        myShapes.clear();
    }

    public void add(Shape shape) {
        myShapes.add(shape);
    }
    public Shape get(int index) {
        return myShapes.get(index);
    }
    public int size() {
        return myShapes.size();
    }
    public List<Circle> getCircles() {
        List<Circle> myCircles = new ArrayList<Circle>();
        for(int i=0; i< this.myShapes.size();i++) {
            if(myShapes.get(i) != null){
                if(myShapes.get(i) instanceof Circle) {
                    myCircles.add((Circle)(myShapes.get(i)));
                }
            }
        }
        return myCircles;
    }
    public List<Rectangle> getRectangles() {
        List<Rectangle> myRectangles = new ArrayList<Rectangle>();
        for(int i=0; i< this.myShapes.size();i++) {
            if((myShapes.get(i) != null) && (myShapes.get(i) instanceof Rectangle)) {
                myRectangles.add((Rectangle)(myShapes.get(i)));
            }
        }
        return myRectangles;
    }
    public List<Triangle> getTriangles() {
        List<Triangle> myTriangles = new ArrayList<Triangle>();
        for(int i=0;i< this.myShapes.size();i++) {
            if((myShapes.get(i) != null) && (myShapes.get(i) instanceof Triangle)) {
                myTriangles.add((Triangle)(myShapes.get(i)));
            }
        }
        return myTriangles;
    }
    public List<ConvexPolygon> getConvexPolygons() {
        List<ConvexPolygon> myPolygons = new ArrayList<ConvexPolygon>();
        for(int i=0; i< myShapes.size(); i++) {
            if((myShapes.get(i) != null) && (myShapes.get(i) instanceof ConvexPolygon)) {
                myPolygons.add((ConvexPolygon)(myShapes.get(i)));
            }
        }
        return myPolygons;
    }
    public List<Shape> getShapesByColor( Color color) {
        List <Shape> coloredShapes = new ArrayList<Shape>();
        for (int i=0; i< myShapes.size(); i++) {
            if((myShapes.get(i) != null) && (color.equals(myShapes.get(i).getColor()))) {
                coloredShapes.add(myShapes.get(i));
            }
        }
        return coloredShapes;
    }
    public double getAreaOfAllShapes() {
        double sum = 0.0;
        for(int i=0; i< myShapes.size(); i++) {
            if((myShapes.get(i) != null) && (myShapes.get(i) instanceof Shape)) {
                sum = sum + myShapes.get(i).getArea();
            }
        }
        return sum;
    }
    public double getPerimeterOfAllShapes() {
        double sum = 0.0;
        for(int i=0; i< myShapes.size(); i++) {
            if((myShapes.get(i) != null) && (myShapes.get(i) instanceof Shape)) {
                sum = sum + myShapes.get(i).getPerimeter();
            }
        }
        return sum;
    }
}
