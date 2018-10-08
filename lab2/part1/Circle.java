public class Circle
{
    private final double RADIUS; // initializing blank final variables
    private final Point MY_CENTER;

    public Circle(Point center, double r) {
        MY_CENTER = new Point(center.getX(), center.getY());
        if (setRadius(r)==true) {
            System.out.println("Radius set to: "+r);
        }
        else { 
            System.out.println("Invalid radius..."); 
        }
    }
    private boolean setRadius(double r) {
        if(r>0) {
            RADIUS = r;
            return true;
        }
        else {
            return false;
        }
    }
    public Point getCenter () {
        return MY_CENTER;
    }
    public double getRadius() {
        return RADIUS;
    }
}
