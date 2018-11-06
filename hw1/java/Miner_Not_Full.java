import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Miner_Not_Full extends Miner
{
   private static final String MINER_KEY = "miner";
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_ID = 1;
   private static final int MINER_COL = 2;
   private static final int MINER_ROW = 3;
   private static final int MINER_LIMIT = 4;
   private static final int MINER_ACTION_PERIOD = 5;
   private static final int MINER_ANIMATION_PERIOD = 6;

   public Miner_Not_Full(
           String id,
           Point position,
           List<PImage> images,
           int resourceLimit,
           int resourceCount,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               EntityKind.MINER_NOT_FULL,
               id,
               position,
               images,
               resourceLimit,
               resourceCount,
               actionPeriod,
               animationPeriod) ;
   }
   
   public Point newPosition(
           int horiz,
           Point newPos,
           WorldModel world,
           Point destPos)
   {
       /*
      int horiz = Integer.signum(destPos.getX() - this.position.getX());
      Point newPos = new Point(this.position.getX() + horiz,
         this.position.getY());
*/
      if (horiz == 0 || world.isOccupied(newPos))
      {
         int vert = Integer.signum(destPos.getY() - this.position.getY());
         newPos = new Point(this.position.getX(),
            this.position.getY() + vert);

         if (vert == 0 || world.isOccupied(newPos))
         {
            newPos = this.position;
         }
      }

      return newPos;
   }
   
   public boolean moveTo(
           WorldModel world,
           Entity target,
           EventScheduler scheduler)
   {
      if (Point.adjacent(this.getPosition(), target.getPosition()))
      {
         this.resourceCount += 1;
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else
      {
         Point nextPos = this.nextPosition(world, target.getPosition());

         if (!this.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }
   
   public boolean transformMiner(
           WorldModel world,
           EventScheduler scheduler,
           ImageStore imageStore)
   {
      if (this.resourceCount >= this.resourceLimit)
      {
         Entity miner = new Miner_Full(this.id, this.position, 
                 this.images, this.resourceLimit,
                 this.resourceLimit, this.actionPeriod,
                 this.animationPeriod );

         world.removeEntity(this);
         scheduler.unscheduleAllEvents(this);

         world.addEntity(miner);
         ((ActiveEntity)miner).scheduleActions(scheduler, world, imageStore);

         return true;
      }

      return false;
   }
   
   public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = world.findNearest(this.position,
         EntityKind.ORE);

      if (!notFullTarget.isPresent() ||
         !this.moveTo(world, notFullTarget.get(), scheduler) ||
         !this.transformMiner(world, scheduler, imageStore))
      {
         scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            this.actionPeriod);
      }
   }
}
