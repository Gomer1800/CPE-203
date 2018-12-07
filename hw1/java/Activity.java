final class Activity implements Action
{
   private final ActionKind kind = ActionKind.ACTIVITY;
   private final  Entity entity;
   private final  WorldModel world;
   private final  ImageStore imageStore;
   private final  int repeatCount;

   public Activity(Entity entity, WorldModel world,
      ImageStore imageStore, int repeatCount)
   {
      // this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }
   
   public void executeAction(EventScheduler scheduler)
   {
      switch (this.entity.getKind())
      {
      case MINER_FULL:
      case MINER_NOT_FULL:
      case ORE:
      case ORE_BLOB:
      case QUAKE:
      case BONES:
      case ZOMBIE:
      case PLAGUE_CLOUD:
      case TOMB:
      case VEIN:
         ((ActiveEntity)this.entity).executeActivity(this.world, this.imageStore,
            scheduler);
         break;
      default:
         throw new UnsupportedOperationException(
            String.format("executeActivityAction not supported for %s",
            this.entity.getKind()));
      }
   }
}
