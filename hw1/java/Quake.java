import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Quake extends AnimatedEntity
{
   private static final String QUAKE_KEY = "quake";
   private static final String QUAKE_ID = "quake";
   private static final int QUAKE_ACTION_PERIOD = 1100;
   private static final int QUAKE_ANIMATION_PERIOD = 100;
   private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
   
   public Quake( Point position, List<PImage> images )
   {
       super(
               EntityKind.QUAKE,
               QUAKE_ID,
               position,
               images,
               QUAKE_ACTION_PERIOD,
               QUAKE_ANIMATION_PERIOD );
   }
   
   public void executeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }
}
