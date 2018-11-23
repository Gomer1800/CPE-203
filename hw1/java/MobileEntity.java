import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.lang.Math;


public abstract class MobileEntity extends AnimatedEntity implements PathingStrategy
{ 
   protected MobileEntity(
           EntityKind kind, 
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               kind,
               id,
               position,
               images,
               actionPeriod,
               animationPeriod);
   }

   public Point nextPosition(
           WorldModel world,
           Point destPos ) {

       int horiz = Integer.signum(destPos.getX() - this.position.getX());
       Point newPos = new Point(this.position.getX() + horiz,
               this.position.getY());

       newPos = newPosition( horiz, newPos, world, destPos );
       return newPos;
           }
   // Sinle Step path finding
   public List<Point> computePath(
           Point start, 
           Point end,
           Predicate<Point> canPassThrough,
           BiPredicate<Point, Point> withinReach,
           Function<Point,
           Stream<Point>> potentialNeighbors)
   {
        /* Does not check withinReach.  Since only a single step is taken
         * *        * on each call, the caller will need to check if the destination
         * *               * has been reached.
         * *                      */
       return potentialNeighbors.apply(start)
           .filter(canPassThrough)
           .filter(pt ->
                   !pt.equals(start)
                   && !pt.equals(end)
                   && Math.abs(end.getX() - pt.getX()) <= Math.abs(end.getX() - start.getX())
                   && Math.abs(end.getY() - pt.getY()) <= Math.abs(end.getY() - start.getY()))
           .limit(1)
           .collect(Collectors.toList());
   }

   // ABSTRACT METHODS
   public abstract Point newPosition(
           int horiz,
           Point newPos,
           WorldModel world,
           Point destPos) ;

   public abstract void executeActivity (
           WorldModel world,
           ImageStore imageStore,
           EventScheduler scheduler );

   public abstract boolean moveTo (
           WorldModel world,
           Entity target,
           EventScheduler scheduler);
}
