public class Bigger {
    public static double whichIsBigger (Circle c, Rectangle r, Polygon p) {
        double biggest = Util.perimeter(c);
        if (biggest < Util.perimeter(r)) {
            biggest = Util.perimeter(r);
        }
        if(biggest < Util.perimeter(p)) {
            biggest =Util.perimeter(p);
        }
        return biggest;
    }
}
