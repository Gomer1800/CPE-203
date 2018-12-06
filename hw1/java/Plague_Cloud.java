import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Plague_Cloud extends AnimatedEntity
{
   private final Random rand = new Random();

   private static final String PLAGUE_CLOUD_KEY = "plague";
   private static final String PLAGUE_CLOUD_ID = "plague";
   private static final int PLAGUE_CLOUD_ACTION_PERIOD = 1100;
   private static final int PLAGUE_CLOUD_ANIMATION_PERIOD = 100;
   public static final int PLAGUE_CLOUD_ANIMATION_REPEAT_COUNT = 10;
   
   private static final String ZOMBIE_KEY = "zombie";
   private static final String ZOMBIE_ID_SUFFIX = " -- zombie";
   //private static final int ZOMBIE_ACTION_PERIOD = 5;
   //private static final int ZOMBIE_ANIMATION_PERIOD = 6;
   private static final int ZOMBIE_PERIOD_SCALE = 4;
   private static final int ZOMBIE_ANIMATION_MIN = 50;
   private static final int ZOMBIE_ANIMATION_MAX = 150;
   
   public Plague_Cloud( Point position, List<PImage> images )
   {
       super(
               EntityKind.PLAGUE_CLOUD,
               PLAGUE_CLOUD_ID,
               position,
               images,
               PLAGUE_CLOUD_ACTION_PERIOD,
               PLAGUE_CLOUD_ANIMATION_PERIOD );
   }
   
   public void executeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = this.position;

      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);

      Entity zombie = new Zombie(
              this.id + ZOMBIE_ID_SUFFIX, pos,
              imageStore.getImageList(ZOMBIE_KEY),
              this.actionPeriod / ZOMBIE_PERIOD_SCALE,
              ZOMBIE_ANIMATION_MIN + this.rand.nextInt(ZOMBIE_ANIMATION_MAX - ZOMBIE_ANIMATION_MIN) );

      world.addEntity(zombie);
      ((ActiveEntity) zombie).scheduleActions(scheduler, world, imageStore);
   }
}
