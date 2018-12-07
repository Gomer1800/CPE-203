import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public abstract class ActiveEntity extends Entity
{
   protected final int actionPeriod;
   
   protected ActiveEntity(
           EntityKind kind,
           String id, 
           Point position,
           List<PImage> images, 
           int actionPeriod)
   {
       super( kind,
               id,
               position,
               images);
       this.actionPeriod = actionPeriod;
   }
    
   public int getActionPeriod() { return this.actionPeriod; } // matches all

   public Action createActivityAction (WorldModel world, ImageStore imageStore ) { // matches all
       return new Activity ( this, world, imageStore, 0); 
   }

   public abstract void executeActivity ( 
           WorldModel world, 
           ImageStore imageStore, 
           EventScheduler scheduler ) ;

   public void scheduleActions(
           EventScheduler scheduler,
           WorldModel world,
           ImageStore imageStore)
   {
       switch (super.kind)
       {
           case MINER_FULL:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               scheduler.scheduleEvent(
                       this, 
                       ((AnimatedEntity)this).createAnimationAction(0),
                       ((AnimatedEntity)this).getAnimationPeriod());
               break;
           case MINER_NOT_FULL:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               scheduler.scheduleEvent(
                       this,
                       ((AnimatedEntity)this).createAnimationAction(0), 
                       ((AnimatedEntity)this).getAnimationPeriod());
               break;

           case BONES:
           case ORE:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               break;

           case ZOMBIE:    
           case ORE_BLOB:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               scheduler.scheduleEvent(
                       this,
                       ((AnimatedEntity)this).createAnimationAction(0), 
                       ((AnimatedEntity)this).getAnimationPeriod());
               break;
          
           case PLAGUE_CLOUD:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               scheduler.scheduleEvent(
                       this,
                       ((AnimatedEntity)this).createAnimationAction(Plague_Cloud.PLAGUE_CLOUD_ANIMATION_REPEAT_COUNT),
                       ((AnimatedEntity)this).getAnimationPeriod());
               break;

           case QUAKE:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               scheduler.scheduleEvent(
                       this,
                       ((AnimatedEntity)this).createAnimationAction(Quake.QUAKE_ANIMATION_REPEAT_COUNT),
                       ((AnimatedEntity)this).getAnimationPeriod());
               break;

           case TOMB:
           case VEIN:
               scheduler.scheduleEvent(
                       this,
                       this.createActivityAction(world, imageStore),
                       this.actionPeriod);
               break;
           
           default:
       }
   }
}
