import java.util.List;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.lang.Math;

class AstarPathingStrategy
    implements PathingStrategy {

    private List<Point> path;

    public List<Point> computePath(
            Point start, 
            Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors)
    {
        /* Does not check withinReach.  Since only a single step is taken
         * *        * on each call, the caller will need to check if the destination
         * *               * has been reached.
         * *                      */

        List<Point> path = 
            potentialNeighbors.apply(start)
            .filter(canPassThrough)
            .filter(pt ->
                    !pt.equals(start)
                    && !pt.equals(end))
                    // && Math.abs(end.getX() - pt.getX()) <= Math.abs(end.getX() - start.getX())
                    // && Math.abs(end.getY() - pt.getY()) <= Math.abs(end.getY() - start.getY()))
            .collect(Collectors.toList());

        List<Point> nextPoint = new LinkedList<Point>();

        if (path.size() != 0) {

            int min = Point.distanceSquared(path.get(0), end);

            for (Point thisPoint : path) {
                if (Point.distanceSquared(thisPoint, end) <= min)
                    nextPoint.add(thisPoint);
            }
            
            return nextPoint;
        }
        else return path;

    }

    /*
   private boolean depthFirstSearch(Point node)
   {
      System.out.println("( "+node.x+" , "+node.y+" )");

      if (!withinBounds(node, grid)) { return false ;}
      if (this.grid[node.y][node.x] == null) { return false ;} // Default Case
      if (this.grid[node.y][node.x] == GridValues.GOAL) { return true ;}
      if (this.grid[node.y][node.x] == GridValues.OBSTACLE) { return false ;}
      if (this.grid[node.y][node.x] == GridValues.SEARCHED) { return false ;}
          
      this.grid[node.y][node.x] = GridValues.SEARCHED ;

      boolean found = (
              depthFirstSearch( new Point(node.x, node.y -1)) ||
              depthFirstSearch( new Point(node.x , node.y +1)) || 
              depthFirstSearch( new Point(node.x +1, node.y)) ||
              depthFirstSearch( new Point(node.x -1, node.y))
              ) ;
      if (found) { 
          this.path.offerFirst(node) ;
      }
      return found;
   }*/

   // From Worksheet
   private static boolean withinBounds( Point p, WorldModel world) {
       return p.getY() >= 0 && p.getY() < world.getNumRows()
           && p.getX() >= 0 && p.getX() < world.getNumCols() ;
   }

   private static boolean neighbors( Point p1, Point p2) {
       return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY()
           || p1.getX()-1 == p2.getX() && p1.getY() == p2.getY()
           || p1.getX() == p2.getX() && p1.getY()+1 == p2.getY()
           || p1.getX() == p2.getX() && p1.getY()-1 == p2.getY() ;
   }
}

