public class Circle
{
    private final double RADIUS; // initializing blank final variables
    private final Point MY_CENTER;

    public Circle(Point center, double r) {
        MY_CENTER = new Point(center.getX(), center.getY());
        if(r>0) {
            RADIUS = r;
            System.out.println("Radius set to: "+r);
        }
        else {
            RADIUS = 1.0;
            System.out.println("Invalid input, radius set to default 1.0");
        }
    }
    public double perimeter() {
        return 2*Math.PI*this.RADIUS;
    }
    public Point getCenter () {
        return MY_CENTER;
    }
    public double getRadius() {
        return RADIUS;
    }
}
