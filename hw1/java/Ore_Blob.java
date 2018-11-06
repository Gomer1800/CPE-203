import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Ore_Blob extends MobileEntity
{
   private static final String BLOB_KEY = "blob";
   private static final String BLOB_ID_SUFFIX = " -- blob";
   private static final int BLOB_PERIOD_SCALE = 4;
   private static final int BLOB_ANIMATION_MIN = 50;
   private static final int BLOB_ANIMATION_MAX = 150;

   private static final String ORE_ID_PREFIX = "ore -- ";
   private static final int ORE_CORRUPT_MIN = 20000;
   private static final int ORE_CORRUPT_MAX = 30000;
   private static final int ORE_REACH = 1;

   private static final String QUAKE_KEY = "quake";

   public Ore_Blob(
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               EntityKind.ORE_BLOB,
               id,
               position,
               images,
               actionPeriod,
               animationPeriod);
   }

   public Point newPosition( int horiz, Point newPos, WorldModel world, Point destPos)
   {
       /*
      int horiz = Integer.signum(destPos.getX() - this.position.getX());
      Point newPos = new Point(this.position.getX() + horiz,
         this.position.getY());
*/
      Optional<Entity> occupant = world.getOccupant(newPos);

      if (horiz == 0 ||
         (occupant.isPresent() && !(occupant.get().getKind() == EntityKind.ORE)))
      {
         int vert = Integer.signum(destPos.getY() - this.position.getY());
         newPos = new Point(this.position.getX(), this.position.getY() + vert);
         occupant = world.getOccupant(newPos);

         if (vert == 0 ||
            (occupant.isPresent() && !(occupant.get().getKind() == EntityKind.ORE)))
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
      Optional<Entity> blobTarget = world.findNearest(this.position, EntityKind.VEIN);
      long nextPeriod = this.actionPeriod;

      if (blobTarget.isPresent())
      {
         Point tgtPos = blobTarget.get().getPosition();

         if (this.moveTo(world, blobTarget.get(), scheduler))
         {
            Entity quake = new Quake(tgtPos,
                    imageStore.getImageList(QUAKE_KEY));

            world.addEntity(quake);
            nextPeriod += this.actionPeriod;
            ((Quake) quake).scheduleActions(scheduler, world, imageStore);
         }
      }

      scheduler.scheduleEvent(this,
         this.createActivityAction(world, imageStore),
         nextPeriod);
   }
}
