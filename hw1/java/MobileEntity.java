import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public abstract class MobileEntity extends AnimatedEntity
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
