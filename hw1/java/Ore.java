import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Ore extends ActiveEntity
{
   private final Random rand = new Random();

   private static final String BLOB_KEY = "blob";
   private static final String BLOB_ID_SUFFIX = " -- blob";
   private static final int BLOB_PERIOD_SCALE = 4;
   private static final int BLOB_ANIMATION_MIN = 50;
   private static final int BLOB_ANIMATION_MAX = 150;

   private static final String ORE_ID_PREFIX = "ore -- ";
   private static final int ORE_CORRUPT_MIN = 20000;
   private static final int ORE_CORRUPT_MAX = 30000;
   private static final int ORE_REACH = 1;
   
   private static final String ORE_KEY = "ore";
   private static final int ORE_NUM_PROPERTIES = 5;
   private static final int ORE_ID = 1;
   private static final int ORE_COL = 2;
   private static final int ORE_ROW = 3;
   private static final int ORE_ACTION_PERIOD = 4;
   
   public Ore(
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod)
   {
       super(
               EntityKind.ORE,
               id,
               position,
               images,
               actionPeriod);
   }
   
   public void executeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Point thisPos = this.position;  // store current position before removing

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      Entity blob = new Ore_Blob(
              this.id + BLOB_ID_SUFFIX, 
              thisPos,
              imageStore.getImageList(BLOB_KEY), 
              this.actionPeriod / BLOB_PERIOD_SCALE,
              BLOB_ANIMATION_MIN + this.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN) );

      world.addEntity(blob);
      ((ActiveEntity) blob).scheduleActions(scheduler, world, imageStore);
   }
}
