final class Action
{
   private final ActionKind kind;
   private final  Entity entity;
   private final  WorldModel world;
   private final  ImageStore imageStore;
   private final  int repeatCount;

   public Action(ActionKind kind, Entity entity, WorldModel world,
      ImageStore imageStore, int repeatCount)
   {
      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }
   // Accessors
   // METHODS
   public void executeAction(EventScheduler scheduler) {
       switch (this.kind) {
           case ACTIVITY:
               this.executeActivityAction(scheduler);
               break;

            case ANIMATION:
               this.executeAnimationAction(scheduler);
               break;
       }
   }
   private void executeAnimationAction(EventScheduler scheduler) {
       Functions.nextImage(this.entity);

       if(this.repeatCount != 1) {
           Functions.scheduleEvent(scheduler, this.entity,
                   Functions.createAnimationAction(this.entity,
                       Math.max(this.repeatCount - 1, 0)),
                   Functions.getAnimationPeriod(this.entity));
       }
   }

   private void executeActivityAction(EventScheduler scheduler)
   {
      switch (this.entity.kind)
      {
      case MINER_FULL:
         Functions.executeMinerFullActivity(this.entity, this.world,
            this.imageStore, scheduler);
         break;

      case MINER_NOT_FULL:
         Functions.executeMinerNotFullActivity(this.entity, this.world,
            this.imageStore, scheduler);
         break;

      case ORE:
         Functions.executeOreActivity(this.entity, this.world, this.imageStore,
            scheduler);
         break;

      case ORE_BLOB:
         Functions.executeOreBlobActivity(this.entity, this.world,
            this.imageStore, scheduler);
         break;

      case QUAKE:
         Functions.executeQuakeActivity(this.entity, this.world, this.imageStore,
            scheduler);
         break;

      case VEIN:
         Functions.executeVeinActivity(this.entity, this.world, this.imageStore,
            scheduler);
         break;

      default:
         throw new UnsupportedOperationException(
            String.format("executeActivityAction not supported for %s",
            this.entity.kind));
      }
   }
}
