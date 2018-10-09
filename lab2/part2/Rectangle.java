public class Rectangle
{
    private final Point TOP_LEFT;
    private final Point BOTTOM_RIGHT;

    public Rectangle (Point topLeft, Point bottomRight) {
        if(topLeft.equals(bottomRight) || (topLeft.getX() != 0.0 && bottomRight.getY() != 0.0 )) {
            TOP_LEFT = new Point(0.0, 1.0);
            BOTTOM_RIGHT = new Point (1.0, 0.0);
            System.out.println("Invalid inputs, default vertices assigned...Unit Rectangle in quadrant I");
        }
        else {
            TOP_LEFT = topLeft;
            BOTTOM_RIGHT = bottomRight;
        }
    }
    public double perimeter() {
        return this.TOP_LEFT.getRadius() *2 +  this.BOTTOM_RIGHT.getRadius()*2;
    }
    public Point getTopLeft() {
        return TOP_LEFT;
    }
    public Point getBottomRight() {
        return BOTTOM_RIGHT;
    }
}
