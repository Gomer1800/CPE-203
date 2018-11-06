import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Miner_Full extends Miner
{
   private static final String MINER_KEY = "miner";
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_ID = 1;
   private static final int MINER_COL = 2;
   private static final int MINER_ROW = 3;
   private static final int MINER_LIMIT = 4;
   private static final int MINER_ACTION_PERIOD = 5;
   private static final int MINER_ANIMATION_PERIOD = 6;

   public Miner_Full(
           String id, 
           Point position,
           List<PImage> images,
           int resourceLimit,
           int resourceCount,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               EntityKind.MINER_FULL,
               id,
               position,
               images,
               resourceLimit,
               resourceCount,
               actionPeriod,
               animationPeriod);
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
      if (Point.adjacent(this.position, target.getPosition()))
      {
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

            world.moveEntity(((Entity)this), nextPos);
         }
         return false;
      }
   }
   
   public boolean transformMiner(
           WorldModel world,
           EventScheduler scheduler, 
           ImageStore imageStore)
   {
      Entity miner = new Miner_Not_Full(this.id, this.position,
              this.images, this.resourceLimit, 
              0 , this.actionPeriod, 
              this.animationPeriod );

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      world.addEntity( miner );
      ((Miner_Not_Full)miner).scheduleActions(scheduler, world, imageStore);

      return true;
   }

   public void executeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = world.findNearest(this.position,
         EntityKind.BLACKSMITH);

      if (fullTarget.isPresent() &&
         this.moveTo(world, fullTarget.get(), scheduler))
      {
         this.transformMiner(world, scheduler, imageStore);
      }
      else
      {
          scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            this.actionPeriod);
      }
   }
}
