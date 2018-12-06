import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Zombie extends MobileEntity
{
   private static final String ZOMBIE_KEY = "zombie";
   private static final int ZOMBIE_NUM_PROPERTIES = 7;
   private static final int ZOMBIE_ID = 1;
   private static final int ZOMBIE_COL = 2;
   private static final int ZOMBIE_ROW = 3;
   private static final int ZOMBIE_LIMIT = 4;
   private static final int ZOMBIE_ACTION_PERIOD = 5;
   private static final int ZOMBIE_ANIMATION_PERIOD = 6;

   public Zombie(
           String id, 
           Point position,
           List<PImage> images,
           //int resourceLimit,
           //int resourceCount,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               EntityKind.ZOMBIE,
               id,
               position,
               images,
               //resourceLimit,
               //resourceCount,
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
   
   public boolean consumeMiner(
           Entity target,
           WorldModel world,
           EventScheduler scheduler, 
           ImageStore imageStore)
   {
      Entity zombie = new Zombie(this.id, target.position,
              this.images,
              this.actionPeriod, 
              this.animationPeriod );

      world.removeEntity(target);
      scheduler.unscheduleAllEvents(target);

      world.addEntity( zombie );
      ((Zombie)zombie).scheduleActions(scheduler, world, imageStore);

      return true;
   }

   public void executeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notfullTarget = world.findNearest(this.position,
         EntityKind.MINER_NOT_FULL);
      
      Optional<Entity> fullTarget = world.findNearest(this.position,
         EntityKind.MINER_FULL);

      if (notfullTarget.isPresent() &&
         this.moveTo(world, notfullTarget.get(), scheduler))
      {
         this.consumeMiner(notfullTarget.get(), world, scheduler, imageStore);
      }
      else if (fullTarget.isPresent() &&
         this.moveTo(world, fullTarget.get(), scheduler))
      {
         this.consumeMiner(fullTarget.get(), world, scheduler, imageStore);
      }
      else
      {
          scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            this.actionPeriod);
      }
   }
}
