import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Entity
{
   public EntityKind kind;
   public String id;
   public Point position;
   public List<PImage> images;
   public int imageIndex;
   public int resourceLimit;
   public int resourceCount;
   public int actionPeriod;
   public int animationPeriod;

   public static final String BLOB_KEY = "blob";
   public static final String BLOB_ID_SUFFIX = " -- blob";
   public static final int BLOB_PERIOD_SCALE = 4;
   public static final int BLOB_ANIMATION_MIN = 50;
   public static final int BLOB_ANIMATION_MAX = 150;

   public static final String ORE_ID_PREFIX = "ore -- ";
   public static final int ORE_CORRUPT_MIN = 20000;
   public static final int ORE_CORRUPT_MAX = 30000;
   public static final int ORE_REACH = 1;

   public static final String QUAKE_KEY = "quake";
   public static final String QUAKE_ID = "quake";
   public static final int QUAKE_ACTION_PERIOD = 1100;
   public static final int QUAKE_ANIMATION_PERIOD = 100;
   public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   public static final int COLOR_MASK = 0xffffff;
   public static final int KEYED_IMAGE_MIN = 5;
   private static final int KEYED_RED_IDX = 2;
   private static final int KEYED_GREEN_IDX = 3;
   private static final int KEYED_BLUE_IDX = 4;

   public static final int PROPERTY_KEY = 0;


   public static final String MINER_KEY = "miner";
   public static final int MINER_NUM_PROPERTIES = 7;
   public static final int MINER_ID = 1;
   public static final int MINER_COL = 2;
   public static final int MINER_ROW = 3;
   public static final int MINER_LIMIT = 4;
   public static final int MINER_ACTION_PERIOD = 5;
   public static final int MINER_ANIMATION_PERIOD = 6;

   public static final String OBSTACLE_KEY = "obstacle";
   public static final int OBSTACLE_NUM_PROPERTIES = 4;
   public static final int OBSTACLE_ID = 1;
   public static final int OBSTACLE_COL = 2;
   public static final int OBSTACLE_ROW = 3;

   public static final String ORE_KEY = "ore";
   public static final int ORE_NUM_PROPERTIES = 5;
   public static final int ORE_ID = 1;
   public static final int ORE_COL = 2;
   public static final int ORE_ROW = 3;
   public static final int ORE_ACTION_PERIOD = 4;

   public static final String SMITH_KEY = "blacksmith";
   public static final int SMITH_NUM_PROPERTIES = 4;

   public Entity(EntityKind kind, String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount,
      int actionPeriod, int animationPeriod)
   {
      this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }
   // accessors
   // Methods

   public int getAnimationPeriod()
   {
      switch (this.kind)
      {
      case MINER_FULL:
      case MINER_NOT_FULL:
      case ORE_BLOB:
      case QUAKE:
         return this.animationPeriod;
      default:
         throw new UnsupportedOperationException(
            String.format("getAnimationPeriod not supported for %s",
            this.kind));
      }
   }

   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }

   public void executeMinerFullActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = Functions.findNearest(world, this.position,
         EntityKind.BLACKSMITH);

      if (fullTarget.isPresent() &&
         Functions.moveToFull(this, world, fullTarget.get(), scheduler))
      {
         Functions.transformFull(this, world, scheduler, imageStore);
      }
      else
      {
          scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
      }
   }

   public void executeMinerNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = Functions.findNearest(world, this.position,
         EntityKind.ORE);

      if (!notFullTarget.isPresent() ||
         !Functions.moveToNotFull(this, world, notFullTarget.get(), scheduler) ||
         !Functions.transformNotFull(this, world, scheduler, imageStore))
      {
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
      }
   }

   public void executeOreActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = this.position;  // store current position before removing

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      Entity blob = Functions.createOreBlob(this.id + BLOB_ID_SUFFIX,
         pos, this.actionPeriod / BLOB_PERIOD_SCALE,
         BLOB_ANIMATION_MIN +
            Functions.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
         imageStore.getImageList(BLOB_KEY));

      world.addEntity(blob);
      blob.scheduleActions(scheduler, world, imageStore);
   }

   public void executeOreBlobActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> blobTarget = Functions.findNearest(world,
         this.position, EntityKind.VEIN);
      long nextPeriod = this.actionPeriod;

      if (blobTarget.isPresent())
      {
         Point tgtPos = blobTarget.get().position;

         if (Functions.moveToOreBlob(this, world, blobTarget.get(), scheduler))
         {
            Entity quake = Functions.createQuake(tgtPos,
               imageStore.getImageList(QUAKE_KEY));

            world.addEntity(quake);
            nextPeriod += this.actionPeriod;
            quake.scheduleActions(scheduler, world, imageStore);
         }
      }

      scheduler.scheduleEvent(this,
         Functions.createActivityAction(this, world, imageStore),
         nextPeriod);
   }

   public void executeQuakeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   public void executeVeinActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(this.position);

      if (openPt.isPresent())
      {
         Entity ore = Functions.createOre(ORE_ID_PREFIX + this.id,
            openPt.get(), ORE_CORRUPT_MIN +
               Functions.rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
            imageStore.getImageList(ORE_KEY));
         world.addEntity(ore);
         ore.scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this,
         Functions.createActivityAction(this, world, imageStore),
         this.actionPeriod);
   }

   public void scheduleActions(EventScheduler scheduler,
      WorldModel world, ImageStore imageStore)
   {
      switch (this.kind)
      {
      case MINER_FULL:
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
         scheduler.scheduleEvent(this, Functions.createAnimationAction(this, 0),
            this.getAnimationPeriod());
         break;

      case MINER_NOT_FULL:
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
         scheduler.scheduleEvent(this,
            Functions.createAnimationAction(this, 0), this.getAnimationPeriod());
         break;

      case ORE:
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
         break;

      case ORE_BLOB:
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
         scheduler.scheduleEvent(this,
            Functions.createAnimationAction(this, 0), this.getAnimationPeriod());
         break;

      case QUAKE:
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
         scheduler.scheduleEvent(this,
            Functions.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
            this.getAnimationPeriod());
         break;

      case VEIN:
         scheduler.scheduleEvent(this,
            Functions.createActivityAction(this, world, imageStore),
            this.actionPeriod);
         break;

      default:
      }
   }
}
