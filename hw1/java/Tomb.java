import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Tomb extends ActiveEntity
{
   private final Random rand = new Random();

   private static final String BONES_ID_PREFIX = "bones -- ";
   private static final int BONES_CORRUPT_MIN = 20000;
   private static final int BONES_CORRUPT_MAX = 30000;
   private static final int BONES_REACH = 1;
   
   private static final String BONES_KEY = "bones";
   private static final int BONES_NUM_PROPERTIES = 5;
   private static final int BONES_ID = 1;
   private static final int BONES_COL = 2;
   private static final int BONES_ROW = 3;
   private static final int BONES_ACTION_PERIOD = 4;
   
   private static final String TOMB_KEY = "tomb";
   private static final int TOMB_NUM_PROPERTIES = 5;
   private static final int TOMB_ID = 1;
   private static final int TOMB_COL = 2;
   private static final int TOMB_ROW = 3;

   public Tomb(
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod )
   {
       super(
               EntityKind.TOMB,
               id,
               position,
               images,
               actionPeriod);
   }

   public void executeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(this.position);

      if (openPt.isPresent())
      {
         Entity bones = new Bones(BONES_ID_PREFIX + this.id, 
                 openPt.get(),
                 imageStore.getImageList(BONES_KEY) ,
                 BONES_CORRUPT_MIN + this.rand.nextInt(BONES_CORRUPT_MAX - BONES_CORRUPT_MIN));
         
         world.addEntity(bones);

         ((ActiveEntity)bones).scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this,
         this.createActivityAction(world, imageStore),
         this.actionPeriod);
   }
}
