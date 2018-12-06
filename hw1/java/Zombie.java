import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Zombie extends MobileEntity
{
   //private final int resourceLimit;
   //private int resourceCount;

   private static final String ZOMBIE_KEY = "zombie";
   private static final String ZOMBIE_ID_SUFFIX = " -- zombie";
   private static final int ZOMBIE_PERIOD_SCALE = 4;
   private static final int ZOMBIE_ANIMATION_MIN = 50;
   private static final int ZOMVIE_ANIMATION_MAX = 150;
   // private static final int ZOMBIE_NUM_PROPERTIES = 7;
   // private static final String ZOMBIE_ID = "zombie";
   //private static final int ZOMBIE_COL = 2;
   //private static final int ZOMBIE_ROW = 3;
   //private static final int ZOMBIE_LIMIT = 4;
   //private static final int ZOMBIE_ACTION_PERIOD = 5;
   //private static final int ZOMBIE_ANIMATION_PERIOD = 6;

   private static final String BONES_ID_PREFIX = "bones -- ";
   private static final int BONES_CORRUPT_MIN = 20000;
   private static final int BONES_CORRUPT_MAX = 30000;
   private static final int BONES_REACH = 1;

   private static final String PLAGUE_CLOUD_KEY = "plague";

   public Zombie(
           String id,
           Point position,
           List<PImage> images,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               EntityKind.ZOMBIE,
               id,
               position,
               images,
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
   
   public void executeActivity(
           WorldModel world, 
           ImageStore imageStore, 
           EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = world.findNearest(this.position, EntityKind.MINER_NOT_FULL);
      
      Optional<Entity> FullTarget = world.findNearest(this.position, EntityKind.MINER_FULL);

      if (notFullTarget.isPresent())
      {
         Point tgtPos = notFullTarget.get().getPosition();

         if(this.moveTo(world, notFullTarget.get(), scheduler))
         {
             Entity plague = new Plague_Cloud(
                     tgtPos, 
                     imageStore.getImageList(PLAGUE_CLOUD_KEY)); 

             world.addEntity(plague);
             ((ActiveEntity) plague).scheduleActions(scheduler, world, imageStore);
         }
      }
      else if (FullTarget.isPresent())
      {
         Point tgtPos = FullTarget.get().getPosition();

         if(this.moveTo(world, FullTarget.get(), scheduler))
         {
             Entity plague = new Plague_Cloud(
                     tgtPos, 
                     imageStore.getImageList(PLAGUE_CLOUD_KEY)); 

             world.addEntity(plague);
             ((ActiveEntity) plague).scheduleActions(scheduler, world, imageStore);
         }
      }
   }
   //END
}
