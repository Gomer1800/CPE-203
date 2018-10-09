import java.util.*;

public class Polygon {
   private ArrayList<Point> vertices;

   public Polygon(List<Point> points) {
      vertices = new ArrayList<Point>();
      for (int i=0; i<points.size(); i++) {
         vertices.add(points.get(i));
      }
   }
   public double perimeter() {
         double sum = 0;
         for (int i=0; i<this.vertices.size(); i++) {
             if(i != this.vertices.size()-1) {
                 sum = sum + Math.hypot( this.vertices.get(i+1).getX() - this.vertices.get(i).getX() , this.vertices.get(i+1).getY() - this.vertices.get(i).getY() );
             }
             else {
                 sum = sum + Math.hypot( this.vertices.get(0).getX() - this.vertices.get(i).getX() , this.vertices.get(0).getY() - this.vertices.get(i).getY() );
             }
         }
         return sum;
   }
   public ArrayList<Point> getPoints() {
       return vertices;
   }
} 
