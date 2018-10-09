public class Bigger {
    public static double whichIsBigger (Circle c, Rectangle r, Polygon p) {
        double biggest = c.perimeter();
        if (biggest < r.perimeter()) {
            biggest = r.perimeter();
        }
        if(biggest < p.perimeter()) {
            biggest = p.perimeter();
        }
        return biggest;
    }
}
