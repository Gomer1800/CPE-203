public class Util {
    public static double perimeter(Circle myCircle) {
        return 2*Math.PI*myCircle.getRadius();
    }
    public static Double perimeter(Rectangle myRectangle) {
        return myRectangle.getTopLeft().getRadius() * myRectangle.getBottomRight().getRadius();
    }
    public static Double perimeter(Polygon myPolygon) {
        double sum = 0;
        for (int i=0; i<myPolygon.getPoints().size(); i++) {
            if(i != myPolygon.getPoints().size()-1) {
                sum = sum + Math.hypot( myPolygon.getPoints().get(i+1).getX() - myPolygon.getPoints().get(i).getX() , myPolygon.getPoints().get(i+1).getY() - myPolygon.getPoints().get(i).getY() );
            }
            else {
                sum = sum + Math.hypot( myPolygon.getPoints().get(0).getX() - myPolygon.getPoints().get(i).getX() , myPolygon.getPoints().get(0).getY() - myPolygon.getPoints().get(i).getY() );
            }
        }
        return sum;
    }
}
