import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Bones extends ActiveEntity
{
   private final Random rand = new Random();

   private static final String ZOMBIE_KEY = "zombie";
   private static final String ZOMBIE_ID_SUFFIX = " -- zombie";
   private static final int ZOMBIE_PERIOD_SCALE = 4;
   private static final int ZOMBIE_ANIMATION_MIN = 150;
   private static final int ZOMBIE_ANIMATION_MAX = 200;

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
   
   public Bones(
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod)
   {
       super(
               EntityKind.BONES,
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

      Entity zombie = new Zombie(
              this.id + ZOMBIE_ID_SUFFIX, 
              thisPos,
              imageStore.getImageList(ZOMBIE_KEY), 
              this.actionPeriod / ZOMBIE_PERIOD_SCALE,
              ZOMBIE_ANIMATION_MIN + this.rand.nextInt(ZOMBIE_ANIMATION_MAX - ZOMBIE_ANIMATION_MIN) );
      world.addEntity(zombie);
      ((ActiveEntity) zombie).scheduleActions(scheduler, world, imageStore);
   }
}
