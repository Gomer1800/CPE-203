import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class MobileEntity extends AnimatedEntity
{ 
   protected PathingStrategy SingleStep_Strategy = new SingleStepPathingStrategy();
   protected PathingStrategy Astar_Strategy = new AstarPathingStrategy(); 
   protected final StrategyKind strategy ;

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

       strategy = StrategyKind.A_STAR;
   }

   // Default
   /* public Point nextPosition(
           WorldModel world,
           Point destPos ) {

       int horiz = Integer.signum(destPos.getX() - this.position.getX());
       Point newPos = new Point(this.position.getX() + horiz,
               this.position.getY());

       newPos = newPosition( horiz, newPos, world, destPos );
       return newPos;
           }*/
   // Single Step
   public Point nextPosition(
           WorldModel world,
           Point destPos ) {

       Point newPos = new Point(0,0);
       List<Point> points ;

       switch (this.strategy)
       {
           case DEFAULT:
               int horiz = Integer.signum(destPos.getX() - this.position.getX());
               newPos = new Point(this.position.getX() + horiz,
                       this.position.getY());
               
               newPos = newPosition( horiz, newPos, world, destPos );
               break;
           
           case SINGLE_STEP:

               points = SingleStep_Strategy.computePath( this.position, destPos,
                       p -> withinBounds(p, world) 
                       && world.getOccupancyCell(p) == null,
                       (p1, p2) -> Point.adjacent(p1, p2),
                       PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

               if (points.size() == 0) { newPos = this.position; }
               else newPos = points.get(0);
               break;

           case A_STAR:
               points = Astar_Strategy.computePath( this.position, destPos,
                       p -> withinBounds(p, world)
                       && world.getOccupancyCell(p) == null,
                       (p1, p2) -> Point.adjacent(p1, p2),
                       PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

               if (points.size() == 0) { newPos = this.position; }
               else newPos = points.get(points.size()-1); 
               break;
       }
       return newPos;
   }

   // From Worksheet
   private static boolean withinBounds( Point thisPoint, WorldModel thisWorld) {
       return thisWorld.withinBounds( thisPoint);
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
