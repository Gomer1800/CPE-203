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
       this.entity.nextImage();

       if(this.repeatCount != 1) {
           scheduler.scheduleEvent(this.entity,
                   ((MoveableEntity)this.entity).createAnimationAction(Math.max(this.repeatCount - 1, 0)),
                   ((MoveableEntity)entity).getAnimationPeriod());
       }
   }

   private void executeActivityAction(EventScheduler scheduler)
   {
      switch (this.entity.getKind())
      {
      case MINER_FULL:
         ((ActionableEntity)this.entity).executeActivity(this.world,
            this.imageStore, scheduler);
         break;

      case MINER_NOT_FULL:
         ((ActionableEntity)this.entity).executeActivity(this.world,
            this.imageStore, scheduler);
         break;

      case ORE:
         ((ActionableEntity)this.entity).executeActivity(this.world, this.imageStore,
            scheduler);
         break;

      case ORE_BLOB:
         ((ActionableEntity)this.entity).executeActivity(this.world,
            this.imageStore, scheduler);
         break;

      case QUAKE:
         ((ActionableEntity)this.entity).executeActivity(this.world, this.imageStore,
            scheduler);
         break;

      case VEIN:
         ((ActionableEntity)this.entity).executeActivity(this.world, this.imageStore,
            scheduler);
         break;

      default:
         throw new UnsupportedOperationException(
            String.format("executeActivityAction not supported for %s",
            this.entity.getKind()));
      }
   }
}
