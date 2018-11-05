import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Vein extends ActiveEntity
{
   private final Random rand = new Random();

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
   
   private static final String VEIN_KEY = "vein";
   private static final int VEIN_NUM_PROPERTIES = 5;
   private static final int VEIN_ID = 1;
   private static final int VEIN_COL = 2;
   private static final int VEIN_ROW = 3;

   public Vein(
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod )
   {
       super(
               EntityKind.VEIN,
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
         Entity ore = new Ore(ORE_ID_PREFIX + this.id, 
                 openPt.get(),
                 imageStore.getImageList(ORE_KEY) ,
                 ORE_CORRUPT_MIN + this.rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN));
         
         world.addEntity(ore);

         ((ActiveEntity)ore).scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this,
         this.createActivityAction(world, imageStore),
         this.actionPeriod);
   }
}
